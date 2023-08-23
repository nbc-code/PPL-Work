package reflang;

import java.util.ArrayList;
import java.util.List;


public interface Heap {

    Value ref (Value value);
    Value deref (Value.RefVal loc);
    Value setref (Value.RefVal loc, Value value);
    Value free (Value.RefVal value);
    void setRefName(String name);

    static public class Heap16Bit implements Heap {
        static final int HEAP_SIZE = 65_536;
        String name = ""; // Name of input reference variable

        Value[] _rep = new Value[HEAP_SIZE];
        int index = 0;

        public void setRefName (String inputName) // Set input name
        {
          name = inputName;
        }

        public Value ref (Value value) {
            if(index >= HEAP_SIZE)
                return new Value.DynamicError("Out of memory error");
            Value.RefVal new_loc = new Value.RefVal(index);
            _rep[index++] = value;
            return new_loc;
        }

        public Value deref (Value.RefVal loc) {
            try {
                return _rep[loc.loc()];
            } catch (ArrayIndexOutOfBoundsException e) {
                return new Value.DynamicError("Segmentation fault at access " + loc);
            }
        }

        public Value setref (Value.RefVal loc, Value value) {
            try {
                return _rep[loc.loc()] = value;
            } catch (ArrayIndexOutOfBoundsException e) {
                return new Value.DynamicError("Segmentation fault at access " + loc);
            }
        }

        public Value free (Value.RefVal loc) {
            try {
                _rep[loc.loc()] = null;
                System.out.println("Part 2: Variable Freed : name " + name + " ref value " + loc.tostring()); // Print free info
                return loc;
            } catch (ArrayIndexOutOfBoundsException e) {
                return new Value.DynamicError("Segmentation fault at access " + loc);
            }

        }

        public Heap16Bit(){}
    }

}