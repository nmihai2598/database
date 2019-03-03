import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataBase {
	String name;
	int nr_nodes = 0;
	int max_capacity = 0;
	/* vector in which I save the name of the tables */
	ArrayList<String> Table_Name;
	/* vector in which I save the number of "sons" from the table */
	ArrayList<Integer> Table_R_F;
	/* vector in which I save the structure of each table */
	ArrayList<Entitate> Table;
	/* The vector for the nodes, on each node elements from the table are saved */
	ArrayList<ArrayList<Entitate>> Nodes;

	public DataBase(String name, int No_Nodes, int Max_Capacity) {
		this.name = name;
		this.Table_Name = new ArrayList<String>();
		this.Table_R_F = new ArrayList<Integer>();
		this.Table = new ArrayList<Entitate>();
		this.Nodes = new ArrayList<ArrayList<Entitate>>(No_Nodes);
		this.nr_nodes = No_Nodes;
		this.max_capacity = Max_Capacity;
	}

	public void Create_Table(String name_Table, int R_F, int No_Attributes, Scanner scan) {
		/*
		 * An auxiliar entity is created in which we save the structure of the table(Atributes
		 * and their type)
		 */
		Entitate n = new Entitate(No_Attributes);

		for (int i = 0; i < No_Attributes; i++) {

			n.name_Atribute.add(scan.next());
			n.Type.add(scan.next());

		}

		Table_Name.add(name_Table);
		Table_R_F.add(R_F);
		/*
		 * Each entity has the name of the table so each element can be found easier.
		 * 
		 */
		n.Table_name = name_Table;
		Table.add(n);

	}

	public void Insert_Table(String name_Table, Scanner scan) {
		Entitate insert;
		/*
		 * An entity is created in which we save the data we want to be introduced into the table.		
		 * 
		 */
		for (int i = 0; i < Table_Name.size(); i++) {
			if (Table_Name.get(i).equals(name_Table) == true) {
				insert = new Entitate(Table.get(i).Type.size());
				/*
				 * For the name, the attributes and the type we only pass a reference from the main vector "Table" 
				 * 
				 */
				insert.name_Atribute = Table.get(i).name_Atribute;
				insert.Type = Table.get(i).Type;
				insert.Table_name = Table.get(i).Table_name;
				/* We save the time at which the instance was created so we can do a CLEANUP  */
				insert.time = System.currentTimeMillis();
				/*
				 * We use the for loop to read the attributes of the new entity we wish to add in the data 				 * 
				 */
				for (int k = 0; k < insert.Type.size(); k++) {
					/* the if loop is used so we can know what is the type of the variable we wish to add. */
					if (insert.Type.get(k).equals("Integer")) {
						Obj<Integer> aux = new Obj<Integer>(scan.nextInt());
						insert.Atribute.add(aux.i);
					} else if (insert.Type.get(k).equals("String")) {
						Obj<String> aux = new Obj<String>(scan.next());
						insert.Atribute.add(aux.i);
					} else if (insert.Type.get(k).equals("Float")) {
						Obj<Float> aux = new Obj<Float>(scan.nextFloat());
						insert.Atribute.add(aux.i);
					}

				}
				/* Here we add the read element in the data base. */
				int R_F = Table_R_F.get(i);
				if (Nodes.size() == 0) {
					/*
					 * When the data base is empty a new vector representing the node should be created
					 *
					 */
					for (int j = 0; j < R_F; j++) {
						ArrayList<Entitate> vector = new ArrayList<Entitate>(max_capacity);
						Entitate copie = new Entitate(insert.Atribute.size());
						/*
						 * A copy of the datas is created so that they are not to be lost, it is not just a reference.					 * 
						 */
						insert.clone(copie);
						vector.add(copie);
						Nodes.add(vector);
					}
					/* If it is not empty we can continue adding entities in the data base. */
				} else {
					int j = 0;
					for (j = 0; j < R_F && j < Nodes.size(); j++) {

						Entitate copie = new Entitate(insert.Atribute.size());
						insert.clone(copie);
						if (Nodes.get(j).size() != max_capacity) {
							Nodes.get(j).add(copie);
						} else {
							R_F++;
						}
					} /*
						 * In the case where the nr of nodes available is exceeded but the copies were not added new nodes are created.* 						 
					   */
					for (int k = j; k < R_F; k++) {
						ArrayList<Entitate> vector = new ArrayList<Entitate>(max_capacity);
						Entitate copie = new Entitate(insert.Atribute.size());
						insert.clone(copie);
						vector.add(copie);
						Nodes.add(vector);
					}

				}
				return;
			}
		}
	}

	/* Delete Function*/
	public void Delete(String name_Table, String Key, BufferedWriter write) throws Exception {
		Entitate aux = null;
		for (int i = 0; i < Nodes.size(); i++) {
			for (int j = 0; j < Nodes.get(i).size(); j++) {
				/* We search in the data base the elements which are present in the table with the given name. */
				if (Nodes.get(i).get(j).Table_name.equals(name_Table)) {
					/* We check if the element we are looking for is in the given keys. */
					if (Nodes.get(i).get(j).equals(Key)) {
						aux = Nodes.get(i).get(j);
						Nodes.get(i).remove(j);
					}
				}
			}
		}
		/*
		 * the aux variable saves the variable which is to be deleted, if it is null then the instance was not found.
		 * 
		 */
		if (aux == null) {
			write.write("NO INSTANCE TO DELETE\n");
		}
	}

	/* The Update functions by the same principle as Delete.*/
	public void Update(String name_Table, String Key, Scanner scan, BufferedWriter write) throws Exception {
		Entitate aux = null;

		ArrayList<String> Att_name=new ArrayList<String>();
		ArrayList<String> Att=new ArrayList<String>();
		String[] read=scan.nextLine().split("[ ]+");
		for(int i=1;i<read.length;i+=2) {
			Att_name.add(read[i]);
			Att.add(read[i+1]);
		}
		for (int i = 0; i < Nodes.size(); i++) {
			for (int j = 0; j < Nodes.get(i).size(); j++) {
				if (Nodes.get(i).get(j).Table_name.equals(name_Table)) {
					if (Nodes.get(i).get(j).equals(Key)) {
						aux = Nodes.get(i).get(j);
						/*
						 * We utilize the function update from the Entitate class so we can modify the desired attribute.
						 */
						for (int k = 0; k < Att_name.size(); k++) {
							aux.update(Att_name.get(k), Att.get(k));
							Nodes.get(i).remove(j);
							Nodes.get(i).add(aux);
						}
					}
				}
			}
		}
		if (aux == null) {
			write.write("NO INSTANCE TO UPDATE\n");
		}
	}

	/* Get Function*/
	public void Get(String name_Table, String Key, BufferedWriter write) throws Exception {
		Entitate aux = null;
		for (int i = 0; i < Nodes.size(); i++) {
			for (int j = 0; j < Nodes.get(i).size(); j++) {

				if (Nodes.get(i).get(j).Table_name.equals(name_Table)) {
					/* We look for the variable we want to display.*/
					if (Nodes.get(i).get(j).equals(Key)) {
						/* If we find it we display the node where it is present and we save a reference. */
						write.write("Nod" + (i + 1) + " ");
						aux = Nodes.get(i).get(j);
					}
				}
			}

		}
		/*
		 * In the end if aux is null it means that the value was not found, otherwise we display the elements from aux.
		 * 
		 */
		if (aux == null) {
			write.write("NO INSTANCE FOUND\n");
		} else {
			aux.afisare(write);
			write.write("\n");
		}
	}

	/* Snapshot Function*/
	public void SnapShot(BufferedWriter write) throws IOException {
		/* We verify if the data base is empty */
		if (Nodes.size() == 0) {
			write.write("EMPTY DB\n");
		}
		/* Otherwise we display whatever we find in it.*/
		for (int i = 0; i < Nodes.size(); i++) {
			if (Nodes.get(i).size() != 0) {
				write.write("Nod" + (i + 1) + "\n");
				for (int j = Nodes.get(i).size() - 1; j >= 0; j--) {
					/* We use the method from the Entitate class to display the contents. */
					Nodes.get(i).get(j).afisare(write);
					write.write("\n");
				}
			}
		}
	}

	/* Cleanup Function */
	public void Cleanup(Long time) {
		for (int i = 0; i < nr_nodes; i++) {
			for (int j = 0; j < Nodes.get(i).size(); j++) {
				/*
				 * When we find an element created before the time of the read, the instance is deleted.
				 * 
				 */
				if (Nodes.get(i).get(j).time < time) {
					Nodes.get(i).remove(j);
				}
			}

		}
	}
}
