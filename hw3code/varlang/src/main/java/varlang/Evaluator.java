package varlang;
import static varlang.AST.*;
import static varlang.Value.*;

import java.util.List;
import java.util.ArrayList;

import varlang.AST.AddExp;
import varlang.AST.NumExp;
import varlang.AST.DivExp;
import varlang.AST.MultExp;
import varlang.AST.Program;
import varlang.AST.SubExp;
import varlang.AST.VarExp;
import varlang.AST.Visitor;
import varlang.Env.EmptyEnv;
import varlang.Env.ExtendEnv;


public class Evaluator implements Visitor<Value> {
	public static double key;

	Value valueOf(Program p) {
		Env env = new EmptyEnv();

		List<String> letters = new ArrayList<>(26);
		List<Value> values = new ArrayList<Value>(26);

		for(char alphabet = 'a'; alphabet <= 'z'; alphabet++){ // List for alphabet
			letters.add(alphabet + "");
		}

		int temp = 0;
		for(int i = 0; i < 26; i++) { // List of converted alphabet values
			temp = (int) (letters.get(i).charAt(0));
			NumVal v = new NumVal((double) temp);
			values.add(v);
		}

		for (int i = 0; i < 26; i++) // Set new environment with defined ascii alphabet values
			env = new ExtendEnv(env, letters.get(i), values.get(i));

		// Value of a program in this language is the value of the expression
		return (Value) p.accept(this, env);
	}
	
	@Override
	public Value visit(AddExp e, Env env) {
		List<Exp> operands = e.all();
		double result = 0;
		for(Exp exp: operands) {
			NumVal intermediate = (NumVal) exp.accept(this, env); // Dynamic type-checking
			result += intermediate.v(); //Semantics of AddExp in terms of the target language.
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(NumExp e, Env env) {
		return new NumVal(e.v());
	}

	@Override
	public Value visit(DivExp e, Env env) {
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
	public Value visit(MultExp e, Env env) {
		List<Exp> operands = e.all();
		double result = 1;
		for(Exp exp: operands) {
			NumVal intermediate = (NumVal) exp.accept(this, env); // Dynamic type-checking
			result *= intermediate.v(); //Semantics of MultExp.
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(Program p, Env env) {
		return (Value) p.e().accept(this, env);
	}

	@Override
	public Value visit(SubExp e, Env env) {
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
	public Value visit(VarExp e, Env env) {
		// Previously, all variables had value 42. New semantics.
		return env.get(e.name());
	}

	@Override
	public Value visit(dec e, Env env) {
		List<Exp> operands = e.all();
		double result = 0;
		NumVal temp = (NumVal) operands.get(0).accept(this, env);
		result = key - temp.v();
		return new NumVal(result);
	}

	@Override
	public Value visit(LetExp e, Env env) { // New for varlang.
		List<String> names = e.names();
		List<Exp> value_exps = e.value_exps();
		List<Value> values = new ArrayList<Value>(value_exps.size());
		
		for(Exp exp : value_exps) 
			values.add((Value)exp.accept(this, env));
		
		Env new_env = env;
		for (int i = 0; i < names.size(); i++)
			new_env = new ExtendEnv(new_env, names.get(i), values.get(i));

		return (Value) e.body().accept(this, new_env);		
	}

	@Override
	public Value visit(letunique e, Env env) { // LETUNIQUE
		List<String> names = e.names();
		List<Exp> value_exps = e.value_exps();
		List<Value> values = new ArrayList<Value>(value_exps.size());

		int i = 0, j = 0;

		for(i = 0; i < value_exps.size(); i++) {
			Exp exp = value_exps.get(i);
			values.add((Value) exp.accept(this, env));
			for(j = i+1; j < names.size(); j++){
				String str = names.get(i);
				if(str.equals(names.get(j)))
				{
					throw new Env.LookupException("Error: duplicate found!");
				}
			}
		}

		Env new_env = env;
		for (i = 0; i < names.size(); i++)
			new_env = new ExtendEnv(new_env, names.get(i), values.get(i));

		return (Value) e.body().accept(this, new_env);
	}

	@Override
	public Value visit(lete e, Env env) { // LETE
		List<String> names = e.names();
		List<Exp> value_exps = e.value_exps();
		List<Value> values = new ArrayList<Value>(value_exps.size());


		for(Exp exp : value_exps)
			values.add((Value) exp.accept(this, env));

		NumVal temp = new NumVal(key);
		temp = (NumVal) values.get(0);
		key = temp.v();

		temp = (NumVal) values.get(1);
		temp = new NumVal(temp.v() + key);
		values.set(1, temp);
		key = temp.v();

		Env new_env = env;
		for (int i = 0; i < names.size(); i++)
			new_env = new ExtendEnv(new_env, names.get(i), values.get(i+1));

		return (Value) e.body().accept(this, new_env);
	}
}
