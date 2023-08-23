package typelang;

import java.util.ArrayList;
import java.util.List;

import typelang.AST.*;
import typelang.Env.ExtendEnv;
import typelang.Env.GlobalEnv;
import typelang.Type.*;

public class Checker implements Visitor<Type,Env<Type>> {
	Printer.Formatter ts = new Printer.Formatter();
	Env<Type> initEnv = initialEnv(); //New for definelang
	
	private Env<Type> initialEnv() {
		GlobalEnv<Type> initEnv = new GlobalEnv<Type>();
		
		/* Type for procedure: (read <filename>). Following is same as (define read (lambda (file) (read file))) */
		List<Type> formalTypes = new ArrayList<Type>();
		formalTypes.add(new Type.StringT());
		initEnv.extend("read", new Type.FuncT(formalTypes, new Type.StringT()));

		/* Type for procedure: (require <filename>). Following is same as (define require (lambda (file) (eval (read file)))) */
		formalTypes = new ArrayList<Type>();
		formalTypes.add(new Type.StringT());
		initEnv.extend("eval", new Type.FuncT(formalTypes, new Type.UnitT()));
		
		/* Add type for new built-in procedures here */ 
		
		return initEnv;
	}
	
    Type check(Program p) {
		return (Type) p.accept(this, null);
	}

	public Type visit(Program p, Env<Type> env) {
		Env<Type> new_env = initEnv;

		for (DefineDecl d: p.decls()) {
			Type type = (Type)d.accept(this, new_env);

			if (type instanceof ErrorT) { return type; }

			Type dType = d.type();

			if (!type.typeEqual(dType)) {
				return new ErrorT("Expected " + dType + " found " + type + " in " + ts.visit(d, null));
			}

			new_env = new ExtendEnv<Type>(new_env, d.name(), dType);
		}
		return (Type) p.e().accept(this, new_env);
	}

	public Type visit(VarExp e, Env<Type> env) {
		try {
			return env.get(e.name());
		} catch(Exception ex) {
			return new ErrorT("Variable " + e.name() +
					" has not been declared in " + ts.visit(e, null));
		}
	}

	public Type visit(LetExp e, Env<Type> env) {
		//Q1
		List<String> names = e.names();
		List<Exp> value_exps = e.value_exps();
		List<Type> types = e.varTypes();
		String msg = "Types do not match. See types matched in ";

		for(int i = 0; i < value_exps.size(); i++){
			Type currType = types.get(i);
			Exp currExp = value_exps.get(i);
			Type expType = (Type) currExp.accept(this, env);
			if(currType.typeEqual(expType)){}
			else {
				return new ErrorT(msg + ts.visit(e, null));
			}
		}

		Env<Type> new_env = env;
		for (int index = 0; index < names.size(); index++)
			new_env = new ExtendEnv<Type>(new_env, names.get(index), types.get(index));

		return (Type) e.body().accept(this, new_env);
	}

	public Type visit(DefineDecl d, Env<Type> env) {
		String name = d.name();
		Type t =(Type) d._value_exp.accept(this, env);
		((GlobalEnv<Type>) initEnv).extend(name, t);
		return t;
	}

	public Type visit(LambdaExp e, Env<Type> env) {
		List<String> names = e.formals();
		List<Type> types = e.types();
		String message = "The number of formal parameters and the number of "
				+ "arguments in the function type do not match in ";
		if (types.size() == names.size()) {
			Env<Type> new_env = env;
			int index = 0;
			for (Type argType : types) {
				new_env = new ExtendEnv<Type>(new_env, names.get(index),
						argType);
				index++;
			}

			Type bodyType = (Type) e.body().accept(this, new_env);
			return new FuncT(types,bodyType);
		}
		return new ErrorT(message + ts.visit(e, null));
	}

	public Type visit(CallExp e, Env<Type> env) {
		return null;	}

	public Type visit(IfExp e, Env<Type> env) {
		return null;	}

	public Type visit(CarExp e, Env<Type> env) {
		//Q3a
		Exp e1 = e.arg();
		Type e1_type = (Type) e1.accept(this, env);

		if(e1_type instanceof ErrorT){ return e1_type; }
		if(e1_type instanceof PairT) { return ((PairT) e1_type)._fst; }

		return new ErrorT("The car expect an expression of type Pair, found " + e1_type.tostring() + " in " + ts.visit(e, null));
	}

	public Type visit(CdrExp e, Env<Type> env) {
		//Q3b
		Exp e1 = e.arg();
		Type e1_type = (Type) e1.accept(this, env);

		if(e1_type instanceof ErrorT) { return e1_type; }
		if(e1_type instanceof PairT) { return ((PairT) e1_type)._snd; }

		return new ErrorT("The cdr expect an expression of type Pair, found " + e1_type.tostring() + " in " + ts.visit(e, null));
	}

	public Type visit(ConsExp e, Env<Type> env) {
		Exp fst = e.fst(); 
		Exp snd = e.snd();

		Type t1 = (Type)fst.accept(this, env);
		if (t1 instanceof ErrorT) { return t1; }

		Type t2 = (Type)snd.accept(this, env);
		if (t2 instanceof ErrorT) { return t2; }

		return new PairT(t1, t2);
	}

	public Type visit(ListExp e, Env<Type> env) {
		//Q3c
		List<Exp> exps = e.elems();
		Type list_type = e.type();

		for(int i = 0; i < e.elems().size(); i++){
			Type curr_elem_type = (Type) exps.get(i).accept(this, env);
			if(curr_elem_type instanceof ErrorT) { return curr_elem_type; }
			if(!(curr_elem_type.typeEqual(list_type))){
				return new ErrorT("The " + i + " expression should have type " + list_type.tostring() + " found " + curr_elem_type.tostring() + " in " + ts.visit(e, null));
			}
		}

		return new ListT(list_type);
	}

	public Type visit(NullExp e, Env<Type> env) {
		Exp arg = e.arg();
		Type type = (Type)arg.accept(this, env);
		if (type instanceof ErrorT) { return type; }

		if (type instanceof ListT) { return BoolT.getInstance(); }

		return new ErrorT("The null? expect an expression of type List, found "
				+ type.tostring() + " in " + ts.visit(e, null));
	}

	public Type visit(RefExp e, Env<Type> env) {
		//Solution for Q2A goes here
		Exp e1 = e.value_exp();
		Type t = e.type();
		Type et = (Type) e1.accept(this, env);

		if(t instanceof ErrorT){ return t; }
		if(t.typeEqual(et)){ return new RefT(t); }

		return new ErrorT("The Ref expression expect type " + t.tostring() + " found " + et.tostring() + " in " + ts.visit(e, null));
	}

	public Type visit(DerefExp e, Env<Type> env) {
		return null;	}

	public Type visit(AssignExp e, Env<Type> env) {
		Exp e1_exp = e.lhs_exp();
		Exp e2_exp = e.rhs_exp();
		Type e1_type = (Type) e1_exp.accept(this, env);
		Type e2_type = (Type) e2_exp.accept(this, env);

		if(e1_type instanceof ErrorT){ return e1_type; }
		if(e1_type instanceof RefT && ((RefT) e1_type)._nestType instanceof Type){
			if(e2_type instanceof ErrorT){ return e2_type; }
			if(e2_type.typeEqual(e1_type)){ return e2_type; }
			else{ return new ErrorT("The inner type of the reference type is " + ((RefT) e1_type)._nestType.tostring() + " the rhs type is " + e2_type.tostring() + " in " + ts.visit(e, null)); }
		}

		return new ErrorT("The lhs of the assignment expression expect a reference type found " + e1_type.tostring()+ " in " + ts.visit(e, null));
	}

	public Type visit(FreeExp e, Env<Type> env) {
		Exp exp = e.value_exp();
		Type type = (Type)exp.accept(this, env);

		if (type instanceof ErrorT) { return type; }

		if (type instanceof RefT) { return UnitT.getInstance(); }

		return new ErrorT("The free expression expect a reference type " +
				"found " + type.tostring() + " in " + ts.visit(e, null));
	}

	public Type visit(UnitExp e, Env<Type> env) {
		return Type.UnitT.getInstance();
	}

	public Type visit(NumExp e, Env<Type> env) {
		return NumT.getInstance();
	}

	public Type visit(StrExp e, Env<Type> env) {
		return Type.StringT.getInstance();
	}

	public Type visit(BoolExp e, Env<Type> env) {
		return Type.BoolT.getInstance();
	}

	public Type visit(LessExp e, Env<Type> env) {
		return visitBinaryComparator(e, env, ts.visit(e, null));
	}

	public Type visit(EqualExp e, Env<Type> env) {
		return visitBinaryComparator(e, env, ts.visit(e, null));
	}

	public Type visit(GreaterExp e, Env<Type> env) {
		Exp e1 = e.first_exp();
		Exp e2 = e.second_exp();
		Type e1_type = (Type) e1.accept(this, env);
		Type e2_type = (Type) e2.accept(this, env);

		if(e1_type instanceof ErrorT){ return e1_type; }
		if(e2_type instanceof ErrorT){ return e2_type; }
		if(!(e1_type.typeEqual(e2_type))){
			return new ErrorT("Expected type " + e1_type.tostring() + " found " + e2_type.tostring() + " in " + ts.visit(e, null));
		}

		return BoolT.getInstance();
	}

	private Type visitBinaryComparator(BinaryComparator e, Env<Type> env, String printNode) { return null; }


	public Type visit(AddExp e, Env<Type> env) {
		return visitCompoundArithExp(e, env, ts.visit(e, null));
	}

	public Type visit(DivExp e, Env<Type> env) {
		Exp exp1 = e.fst();
		Exp exp2 = e.snd();
		Type e1_type = (Type) exp1.accept(this, env);
		Type e2_type = (Type) exp2.accept(this, env);

		if(e1_type instanceof ErrorT){ return e1_type; }
		if(e2_type instanceof ErrorT){ return e2_type; }
		if(!(e1_type.typeEqual(e2_type)) || !(e1_type instanceof NumT)){
			return new ErrorT("Expecting type number for both expressions in " + ts.visit(e, null));
		}

		if(((NumExp) exp2)._val == 0.0){
			return new ErrorT("Cannot divide by zero. Check values in " + ts.visit(e, null));
		}

		return visitCompoundArithExp(e, env, ts.visit(e, null));
	}

	public Type visit(MultExp e, Env<Type> env) {
		return visitCompoundArithExp(e, env, ts.visit(e, null));
	}

	public Type visit(SubExp e, Env<Type> env) {
		return visitCompoundArithExp(e, env, ts.visit(e, null));
	}

	private Type visitCompoundArithExp(CompoundArithExp e, Env<Type> env, String printNode) {
		return null;	}

	private static boolean assignable(Type t1, Type t2) {
		if (t2 instanceof UnitT) { return true; }

		return t1.typeEqual(t2);
	}
	
	public Type visit(ReadExp e, Env<Type> env) {
		return UnitT.getInstance();
	}

	public Type visit(EvalExp e, Env<Type> env) {
		return UnitT.getInstance();
	}
}
