import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Tema2 {

	public static void main(String[] args) throws IOException {
		/* We use a Scanner variable to read from files. */
		File file = new File(args[0]);
		Scanner scan = new Scanner(file);
		/*
		 * We use a BufferedWriter variable to write to the file given in the arguments.
		 * 
		 */
		File fout = new File(args[0]+"_out");
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter write = new BufferedWriter(new OutputStreamWriter(fos));

		/* We declare first the data base we wish to create. */
		DataBase data_base = null;
		/* We read each instruction with a while loop. */
		while (scan.hasNext()) {
			String read = scan.next();
			if (read.equals("CREATEDB") == true) {
				String name = scan.next();
				int No_Nodes = scan.nextInt();
				int Max_Capacity = scan.nextInt();
				data_base = new DataBase(name, No_Nodes, Max_Capacity);
			} else if (read.equals("CREATE") == true) {
				String name_Table = scan.next();
				int R_F = scan.nextInt();
				int No_Attributes = scan.nextInt();
				data_base.Create_Table(name_Table, R_F, No_Attributes, scan);
			} else if (read.equals("INSERT") == true) {
				String name_Table = scan.next();
				data_base.Insert_Table(name_Table, scan);
			} else if (read.equals("DELETE") == true) {
				String name_Table = scan.next();
				String Key = scan.next();
				try{
					data_base.Delete(name_Table, Key, write);
				}
				catch (Exception e) {

					write.write("NO INSTANCE FOUND\n");
				}

			} else if (read.equals("SNAPSHOTDB") == true) {
				data_base.SnapShot(write);
			} else if (read.equals("GET") == true) {
				String name_Table = scan.next();
				String Key = scan.next();
				try{
					data_base.Get(name_Table, Key, write);
				}
				catch (Exception e) {

					write.write("NO INSTANCE FOUND\n");
				}
			} else if (read.equals("UPDATE") == true) {
				String name_Table = scan.next();
				String Key = scan.next();
				try{
					data_base.Update(name_Table, Key, scan, write);
				}
				catch (Exception e) {

					write.write("NO INSTANCE FOUND\n");
				}

			} else if (read.equals("CLEANUP") == true) {
				scan.next();
				long time = scan.nextLong();
				data_base.Cleanup(time);
			}
		}
		write.close();
		scan.close();

	}
}