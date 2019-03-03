import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Entitate {
	String Table_name;
	ArrayList<String> name_Atribute;
	ArrayList<String> Type;
	ArrayList<Object> Atribute;
	long time;

	/* The Entity Constructor(Representing the elements from the vector)*/
	public Entitate(int No_Attributes) {
		this.name_Atribute = new ArrayList<String>(No_Attributes);
		this.Type = new ArrayList<String>(No_Attributes);
		this.Atribute = new ArrayList<Object>(No_Attributes);
	}

	/* The Cloning Function */
	public void clone(Entitate aux) {
		/* We save the references from the Table vector situated in the class DataBase. */
		aux.name_Atribute = name_Atribute;/* Numele atributelor */
		aux.Type = Type;/* Tipul atributelor */
		aux.Table_name = Table_name;/* numele tabelului */
		aux.time = time;
		for (int i = 0; i < Atribute.size(); i++) {
			/* For eacj attribute a copy is created in the Atribute vector. */
			if (aux.Type.get(i).equals("Integer")) {
				Integer att = new Integer((int) Atribute.get(i));
				Obj<Integer> aux1 = new Obj<Integer>(att);
				aux.Atribute.add(aux1.i);
			} else if (aux.Type.get(i).equals("String")) {
				String att = new String((String) Atribute.get(i));
				Obj<String> aux1 = new Obj<String>(att);
				aux.Atribute.add(aux1.i);
			} else if (aux.Type.get(i).equals("Float")) {
				Float att = new Float((float) Atribute.get(i));
				Obj<Float> aux1 = new Obj<Float>(att);
				aux.Atribute.add(aux1.i);
			}
		}

	}

	/* Display Function */
	public void afisare(BufferedWriter write) throws IOException {
		write.write(Table_name + " ");
		int n = Atribute.size();
		/* We go over all attributes. */
		int i;
		for ( i = 0; i < n - 1; i++) {
			if (Type.get(i).equals("Float")){
				Integer a=(Integer) Math.round((float) Atribute.get(i));
				Float b=(Float) Atribute.get(i);
				if((b -a)==0) {
				write.write(name_Atribute.get(i) + ":" + a + " ");
				}
				else {
					write.write(name_Atribute.get(i) + ":" + Atribute.get(i) + " ");
				}
			} else {
				write.write(name_Atribute.get(i) + ":" + Atribute.get(i) + " ");
			}
		}n=n-1;
		if (Type.get(n).equals("Float")){
			Integer a=(Integer) Math.round((float) Atribute.get(n));
			Float b=(Float) Atribute.get(n);
			if((b -a)==0) {
			write.write(name_Atribute.get(n) + ":" + a + " ");
			}
			else {
				write.write(name_Atribute.get(n) + ":" + Atribute.get(n) + " ");
			}
		} else {
			write.write(name_Atribute.get(n) + ":" + Atribute.get(n) + " ");
		}

	}

	/* The Update Function */
	public void update(String Att_name, String Att) {
		for (int i = 0; i < name_Atribute.size(); i++) {
			/*
			 * We convert the type of the attribute needed to be changed into the chosen attribute.
			 * 
			 */
			if (Att_name.equals(name_Atribute.get(i))) {
				/* We delete the element first then we replace it. */
				if (Type.get(i).equals("Integer")) {
					Obj<Integer> aux = new Obj<Integer>(Integer.parseInt(Att));
					Atribute.remove(i);
					Atribute.add(i, aux.i);
				} else if (Type.get(i).equals("String")) {
					Obj<String> aux = new Obj<String>(Att);
					Atribute.remove(i);
					Atribute.add(i, aux.i);
				} else if (Type.get(i).equals("Float")) {
					Obj<Float> aux = new Obj<Float>(Float.parseFloat(Att));
					Atribute.remove(i);
					Atribute.add(i, aux.i);
				}
			}
		}
	}

	/* The Function which checks if the element searched for has the key needed. */
	public boolean equals(String Key) throws Exception {
		
		
		if (Type.get(0).equals("Integer")) {// System.out.println(Type.get(0)+" "+Atribute.get(0)+" ");
			Obj<Integer> aux = new Obj<Integer>(Integer.parseInt(Key));
			return (Atribute.get(0).equals(aux.i));
		} else if (Type.get(0).equals("String")) {
			Obj<String> aux = new Obj<String>(Key);
			return (Atribute.get(0).equals(aux.i));
		} else if (Type.get(0).equals("Float")) {
			Obj<Float> aux = new Obj<Float>(Float.parseFloat(Key));
			return (Atribute.get(0).equals(aux.i));
		}
		return false;
	}

}
