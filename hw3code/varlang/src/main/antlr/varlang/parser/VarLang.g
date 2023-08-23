grammar VarLang;

import ArithLang; //Import all rules from Arithlang grammar.
 
 // New elements in the Grammar of this Programming Language
 //  - grammar rules start with lowercase

 exp returns [Exp ast]: 
		v=varexp { $ast = $v.ast; }
		| n=numexp { $ast = $n.ast; }
        | a=addexp { $ast = $a.ast; }
        | s=subexp { $ast = $s.ast; }
        | m=multexp { $ast = $m.ast; }
        | d=divexp { $ast = $d.ast; }
        | l=letexp { $ast = $l.ast; }
        | lu=letunique { $ast = $lu.ast; }
        | le=lete  { $ast = $le.ast; }
        | de=dec  { $ast = $de.ast; }
        ;

 varexp returns [VarExp ast]: 
 		id=Identifier { $ast = new VarExp($id.text); }
 		;

 letexp  returns [LetExp ast] 
        locals [ArrayList<String> names, ArrayList<Exp> value_exps]
 		@init { $names = new ArrayList<String>(); $value_exps = new ArrayList<Exp>(); } :
 		'(' Let 
 			'(' ( '(' id=Identifier e=exp ')' { $names.add($id.text); $value_exps.add($e.ast); } )+  ')'
 			body=exp 
 			')' { $ast = new LetExp($names, $value_exps, $body.ast); }
 		;

 letunique  returns [letunique ast]
        locals [ArrayList<String> names = new ArrayList<String>(), ArrayList<Exp> value_exps = new ArrayList<Exp>(), ArrayList<Exp> list = new ArrayList<Exp>()] :
 		'(' 'letunique'
 			'(' ( '(' id=Identifier e=exp ')' { $names.add($id.text); $value_exps.add($e.ast); } )+  ')'
 			body=exp
 			')' { $ast = new letunique($names, $value_exps, $body.ast); }
 		;

 lete  returns [lete ast]
        locals [ArrayList<String> names = new ArrayList<String>(), ArrayList<Exp> value_exps = new ArrayList<Exp>()] :
 		'(' 'lete'
 		    e=exp { $value_exps.add($e.ast); }
 			'(' ( '(' id=Identifier e=exp ')' { $names.add($id.text); $value_exps.add($e.ast); } )+  ')'
 			body=exp
 			')' { $ast = new lete($names, $value_exps, $body.ast); }
 		;

 dec returns [dec ast]
        locals [ArrayList<Exp> list = new ArrayList<Exp>()] :
   		'(' 'dec'
   		    e=exp { $list.add($e.ast); }
   		    ( e=exp { $list.add($e.ast); } )+
   		')' { $ast = new dec($list); }
   		;

 // Lexical Specification of this Programming Language
 //  - lexical specification rules start with uppercase
