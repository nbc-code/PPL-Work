package typelang;
import static typelang.AST.*;
import static typelang.Heap.*;
import static typelang.Value.*;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

import typelang.Env.*;

public class Evaluator implements Visitor<Value, Env<Value>> {
	
	Printer.Formatter ts = new Printer.Formatter();

	Env<Value> initEnv = initialEnv(); //New for definelang
	Heap heap = new Heap16Bit(); //New for reflang
	
	Value valueOf(Program p) {
		return (Value) p.accept(this, initEnv);
	}
	
	@Override
	public Value visit(AddExp e, Env<Value> env) {
		List<Exp> operands = e.all();
		int result = 0;
		for(Exp exp: operands) {
			NumVal intermediate = (NumVal) exp.accept(this, env); // Dynamic type-checking
			result += intermediate.v(); //Semantics of AddExp in terms of the target language.
		}
		return new NumVal(result);
	}
	
	@Override
	public Value visit(UnitExp e, Env<Value> env) {
		return new UnitVal();
	}

	@Override
	public Value visit(NumExp e, Env<Value> env) {
		return new NumVal(e.v());
	}

	@Override
	public Value visit(StrExp e, Env<Value> env) {
		return new StringVal(e.v());
	}

	@Override
	public Value visit(BoolExp e, Env<Value> env) {
		return new BoolVal(e.v());
	}

	@Override
	public Value visit(DivExp e, Env<Value> env) {
		List<Exp> operands = e.all();
		NumVal lVal = (NumVal) operands.get(0).accept(this, env);
		double result = lVal.v(); 
		for(int i=1; i<operands.size(); i++) {
			NumVal rVal = (NumVal) operands.get(i).accept(this, env);
			result = result / rVal.v();
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(MultExp e, Env<Value> env) {
		List<Exp> operands = e.all();
		double result = 1;
		for(Exp exp: operands) {
			NumVal intermediate = (NumVal) exp.accept(this, env); // Dynamic type-checking
			result *= intermediate.v(); //Semantics of MultExp.
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(Program p, Env<Value> env) {
		try {
			for(DefineDecl d: p.decls())
				d.accept(this, initEnv);
			return (Value) p.e().accept(this, initEnv);
		} catch (ClassCastException e) {
			return new DynamicError(e.getMessage());
		}
	}

	@Override
	public Value visit(SubExp e, Env<Value> env) {
		List<Exp> operands = e.all();
		NumVal lVal = (NumVal) operands.get(0).accept(this, env);
		double result = lVal.v();
		for(int i=1; i<operands.size(); i++) {
			NumVal rVal = (NumVal) operands.get(i).accept(this, env);
			result = result - rVal.v();
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(VarExp e, Env<Value> env) {
		// Previously, all variables had value 42. New semantics.
		return env.get(e.name());
	}	

	@Override
	public Value visit(LetExp e, Env<Value> env) { // New for varlang.
		List<String> names = e.names();
		List<Exp> value_exps = e.value_exps();
		List<Value> values = new ArrayList<Value>(value_exps.size());

		for(Exp exp : value_exps) 
			values.add((Value)exp.accept(this, env));
		
		Env<Value> new_env = env;
		for (int index = 0; index < names.size(); index++)
			new_env = new ExtendEnv<Value>(new_env, names.get(index), values.get(index));

		return (Value) e.body().accept(this, new_env);		
	}	
	
	@Override
	public Value visit(DefineDecl e, Env<Value> env) { // New for definelang.
		String name = e.name();
		Exp value_exp = e.value_exp();
		Value value = (Value) value_exp.accept(this, env);
		((GlobalEnv<Value>) initEnv).extend(name, value);
		return new Value.UnitVal();		
	}	

	@Override
	public Value visit(LambdaExp e, Env<Value> env) { // New for funclang.
		return new Value.FunVal(env, e.formals(), e.body());
	}
	
	@Override
	public Value visit(CallExp e, Env<Value> env) { // New for funclang.
		Object result = e.operator().accept(this, env);
		if(!(result instanceof Value.FunVal))
			return new Value.DynamicError("Operator not a function in call " +  ts.visit(e, null));
		Value.FunVal operator =  (Value.FunVal) result; //Dynamic checking
		List<Exp> operands = e.operands();

		// Call-by-value semantics
		List<Value> actuals = new ArrayList<Value>(operands.size());
		for(Exp exp : operands) 
			actuals.add((Value)exp.accept(this, env));
		
		List<String> formals = operator.formals();
		if (formals.size()!=actuals.size())
			return new Value.DynamicError("Argument mismatch in call " + ts.visit(e, null));

		Env<Value> fun_env = operator.env();
		for (int index = 0; index < formals.size(); index++)
			fun_env = new ExtendEnv<Value>(fun_env, formals.get(index), actuals.get(index));
		
		return (Value) operator.body().accept(this, fun_env);
	}
		
	@Override
	public Value visit(IfExp e, Env<Value> env) { // New for funclang.
		Object result = e.conditional().accept(this, env);
		if(!(result instanceof Value.BoolVal))
			return new Value.DynamicError("Condition not a boolean in expression " +  ts.visit(e, null));
		Value.BoolVal condition =  (Value.BoolVal) result; //Dynamic checking


		if(condition.v())
			return (Value) e.then_exp().accept(this, env);
		else return (Value) e.else_exp().accept(this, env);
	}

	@Override
	public Value visit(LessExp e, Env<Value> env) { // New for funclang.
		Value.NumVal first = (Value.NumVal) e.first_exp().accept(this, env);
		Value.NumVal second = (Value.NumVal) e.second_exp().accept(this, env);
		return new Value.BoolVal(first.v() < second.v());
	}
	
	@Override
	public Value visit(EqualExp e, Env<Value> env) { // New for funclang.
		Value.NumVal first = (Value.NumVal) e.first_exp().accept(this, env);
		Value.NumVal second = (Value.NumVal) e.second_exp().accept(this, env);
		return new Value.BoolVal(first.v() == second.v());
	}

	@Override
	public Value visit(GreaterExp e, Env<Value> env) {
		// New for funclang.
		Exp e1 = e.first_exp();
		Exp e2 = e.second_exp();
		int e1_total = 0;
		int e2_total = 0;
		List<Exp> e1_exps = new ArrayList<>();
		List<Exp> e2_exps = new ArrayList<>();
		Type type = null;

		String one = "one";
		String two = "two";

		if(e1 instanceof NumExp){
			type = new Type.NumT();
		}
		if(e1 instanceof BoolExp){
			type = new Type.BoolT();
		}
		if(e1 instanceof StrExp){
			type = new Type.StringT();
		}
		if(e1 instanceof ListExp){
			type = ((ListExp) e1).type();
			ListExp e1_ListExp = (ListExp) e1;
			ListExp e2_ListExp = (ListExp) e2;
			e1_exps = e1_ListExp.elems();
			e2_exps = e2_ListExp.elems();
		}
		else{
			e1_exps.add(e1);
			e2_exps.add(e2);
		}

		if(e1_exps.size() > e2_exps.size()){ return new BoolVal(e1_exps.size() > e2_exps.size()); }
		else if(e1_exps.size() == e2_exps.size()){
			if(type instanceof Type.BoolT) {
				for(int i = 0; i < e1_exps.size(); i++) {
					BoolExp exp1 = (BoolExp) e1_exps.get(i);
					BoolExp exp2 = (BoolExp) e2_exps.get(i);
					if (exp1._val) { e1_total++; }
					if (exp2._val) { e2_total++; }
				}
			}
			if(type instanceof Type.NumT) {
				for(int i = 0; i < e1_exps.size(); i++) {
					NumExp exp1 = (NumExp) e1_exps.get(i);
					NumExp exp2 = (NumExp) e2_exps.get(i);
					e1_total += exp1._val;
					e2_total += exp2._val;
				}
			}
			if(type instanceof Type.StringT){
				for(int i = 0; i < e1_exps.size(); i++) {
					StrExp exp1 = (StrExp) e1_exps.get(i);
					StrExp exp2 = (StrExp) e2_exps.get(i);
					if(exp1._val.length() > exp2._val.length()){
						e1_total++;
					}
					else if(exp1._val.length() == exp2._val.length()){
						int e1_ascii = 0;
						int e2_ascii = 0;
						for(int j = 0; j < exp1._val.length(); j++){
							e1_ascii += exp1._val.charAt(j);
							e2_ascii += exp2._val.charAt(j);
						}
						if(e1_ascii > e2_ascii){ e1_total++; }
						else{ e2_total++; }
					}
					else{ e2_total++; }
				}
			}
		}

		return new Value.BoolVal(e1_total > e2_total);

		//Value.NumVal first = (Value.NumVal) e.first_exp().accept(this, env);
		//Value.NumVal second = (Value.NumVal) e.second_exp().accept(this, env);

	}
	
	@Override
	public Value visit(CarExp e, Env<Value> env) { 
		Value.PairVal pair = (Value.PairVal) e.arg().accept(this, env);
		return pair.fst();
	}
	
	@Override
	public Value visit(CdrExp e, Env<Value> env) { 
		Value.PairVal pair = (Value.PairVal) e.arg().accept(this, env);
		return pair.snd();
	}
	
	@Override
	public Value visit(ConsExp e, Env<Value> env) { 
		Value first = (Value) e.fst().accept(this, env);
		Value second = (Value) e.snd().accept(this, env);
		return new Value.PairVal(first, second);
	}

	@Override
	public Value visit(ListExp e, Env<Value> env) { // New for funclang.
		List<Exp> elemExps = e.elems();
		int length = elemExps.size();
		if(length == 0)
			return new Value.Null();
		
		//Order of evaluation: left to right e.g. (list (+ 3 4) (+ 5 4)) 
		Value[] elems = new Value[length];
		for(int i=0; i<length; i++)
			elems[i] = (Value) elemExps.get(i).accept(this, env);

		Value result = new Value.Null();
		for(int i=length-1; i>=0; i--) 
			result = new PairVal(elems[i], result);
		return result;
	}	
	
	@Override
	public Value visit(NullExp e, Env<Value> env) {
		Value val = (Value) e.arg().accept(this, env);
		return new BoolVal(val instanceof Value.Null);
	}

    
	@Override
	public Value visit(RefExp e, Env<Value> env) { // New for reflang.
		Exp value_exp = e.value_exp();
		Value value = (Value) value_exp.accept(this, env);
		return heap.ref(value);
	}

	@Override
	public Value visit(DerefExp e, Env<Value> env) { // New for reflang.
		Exp loc_exp = e.loc_exp();
		Value.RefVal loc = (Value.RefVal) loc_exp.accept(this, env);
		return heap.deref(loc);
	}

	@Override
	public Value visit(AssignExp e, Env<Value> env) { // New for reflang.
		Exp rhs = e.rhs_exp();
		Exp lhs = e.lhs_exp();
		//Note the order of evaluation below.
		Value rhs_val = (Value) rhs.accept(this, env);
		Value.RefVal loc = (Value.RefVal) lhs.accept(this, env);
		Value assign_val = heap.setref(loc, rhs_val);
		return assign_val;
	}

	@Override
	public Value visit(FreeExp e, Env<Value> env) { // New for reflang.
		Exp value_exp = e.value_exp();
		Value.RefVal loc = (Value.RefVal) value_exp.accept(this, env);
		heap.free(loc);
		return new Value.UnitVal();
	}

	public Value visit(EvalExp e, Env<Value> env) {
		StringVal programText = (StringVal) e.code().accept(this, env);
		Program p = _reader.parse(programText.v());
		return (Value) p.accept(this, env);
	}

	public Value visit(ReadExp e, Env<Value> env) {
		StringVal fileName = (StringVal) e.file().accept(this, env);
		try {
			String text = Reader.readFile("" + System.getProperty("user.dir") + File.separator + fileName.v());
			return new StringVal(text);
		} catch (IOException ex) {
			return new DynamicError(ex.getMessage());
		}
	}

	private Env<Value> initialEnv() {
		GlobalEnv<Value> initEnv = new GlobalEnv<Value>();
		
		/* Procedure: (read <filename>). Following is same as (define read (lambda (file) (read file))) */
		List<String> formals = new ArrayList<>();
		formals.add("file");
		Exp body = new AST.ReadExp(new VarExp("file"));
		Value.FunVal readFun = new Value.FunVal(initEnv, formals, body);
		initEnv.extend("read", readFun);

		/* Procedure: (require <filename>). Following is same as (define require (lambda (file) (eval (read file)))) */
		formals = new ArrayList<>();
		formals.add("file");
		body = new EvalExp(new AST.ReadExp(new VarExp("file")));
		Value.FunVal requireFun = new Value.FunVal(initEnv, formals, body);
		initEnv.extend("require", requireFun);
		
		/* Add new built-in procedures here */ 
		
		return initEnv;
	}
	
	Reader _reader; 
	public Evaluator(Reader reader) {
		_reader = reader;
	}
}
