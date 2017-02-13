/**
 * @author     Erik Moore <erikmoore93@yahoo.com>
 * @purpose    1.0   Builds a hashmap and allows user to fill, insert, show, find, or delete
 * @date        2013-02-17          
 * @usage		to run this program: $>java HashTableApp main
 * 			
 */

// hash.java
// demonstrates hash table with linear probing
// to run this program: C:>java HashTableApp
import java.io.*;
////////////////////////////////////////////////////////////////
class DataItem
{ // (could have more data)
	private String iData; // data item (key)
	//--------------------------------------------------------------
	public DataItem(String ii) // constructor
	{ iData = ii; }
	//--------------------------------------------------------------
	public String getKey()
	{ return iData; }
	//--------------------------------------------------------------
} // end class DataItem
////////////////////////////////////////////////////////////////
class HashTable
{
	private DataItem[] hashArray; // array holds hash table
	private static int arraySize;
	private DataItem nonItem; // for deleted items
	//-------------------------------------------------------------
	public HashTable(int size) // constructor
	{
		arraySize = size;
		hashArray = new DataItem[arraySize];
		nonItem = new DataItem(""); // deleted item key is ""
	}
	//-------------------------------------------------------------
	public void displayTable()//displays table
	{
		System.out.print("Table: \n");
		for(int j=0; j<arraySize; j++)
		{
			if(hashArray[j] != null)
				System.out.print(j+" "+hashArray[j].getKey() + "\n");
			else
				System.out.print(j+"\n");
		}
		System.out.println("");
	}
	//-------------------------------------------------------------
	public static int hashFunc(String key)
	{
	int hashVal = 0;
	for(int j=0; j<key.length(); j++) // left to right
	{
	int letter = key.charAt(j);// - 96; // get char code
	hashVal = (hashVal * 27 + letter) % arraySize; // mod
	}
	return hashVal; // no mod
	} // end hashFunc()
	//-------------------------------------------------------------
	public void insert(DataItem item) // insert a DataItem
	//(assumes table not full)
	{
		String key = item.getKey(); // extract key
		int hashVal = hashFunc(key); // hash the key
		//until empty cell or "",
		while(hashArray[hashVal] != null &&
				hashArray[hashVal].getKey() != "")
		{
			++hashVal; // go to next cell
			hashVal %= arraySize; // wraparound if necessary
		}
		hashArray[hashVal] = item; // insert item
	} // end insert()
	//-------------------------------------------------------------
	public DataItem delete(String key) // delete a DataItem
	{
		
		int hashVal = hashFunc(key); // hash the key
		int count=0;
		while(hashArray[hashVal] != null) // until empty cell,
		{ // found the key?
			boolean equal = true;//assume true until proven false
			String gotKey = hashArray[hashVal].getKey();
			if(gotKey.length()!=key.length())//check length of strings against each other
			{
				equal = false;//unequal lengths are not equal strings
			}
			else//equal lengths could be equal strings
			{		
				for(int times=0;times<key.length();times++)//move through string
				{
					if(gotKey.charAt(times)!=key.charAt(times))//check individual characters
					{
						equal = false;
					}
				}
				if(equal)//if equal return
				{
					DataItem temp = hashArray[hashVal]; // save item
					hashArray[hashVal] = nonItem; // delete item
					return temp; // return item
				}
			}
			++hashVal; // go to next cell
			hashVal %= arraySize; // wraparound if necessary
			count++;
			if(count >= arraySize)
			{
				System.out.println("could not find value to delete");
				break;
			}
		}
		return null; // can't find item
	} // end delete()
	// -------------------------------------------------------------
	public DataItem find(String key) // find item with key
	{
		int hashVal = hashFunc(key); // hash the key
		while(hashArray[hashVal] != null) // until empty cell,
		{ // found the key?
			boolean equal = true;
			String gotKey = hashArray[hashVal].getKey();
			if(gotKey.length()!=key.length())//check length of strings against each other
			{
				equal = false;//different lengths aren't equal
			}
			else//same length could be equal
			{		
				for(int times=0;times<key.length();times++)//move through string
				{
					if(gotKey.charAt(times)!=key.charAt(times))//all characters must be equal
					{
						equal = false;
					}
				}
				if(equal)
				{
					return hashArray[hashVal]; // yes, return item
				}
			}
			++hashVal; // go to next cell
			hashVal %= arraySize; // wraparound if necessary
		}
		return null; // can't find item
	}
	// -------------------------------------------------------------
} // end class HashTable
////////////////////////////////////////////////////////////////
class HashTableApp
{
	public static void main(String[] args) throws IOException
	{
		DataItem aDataItem;
		int size, n, keysPerCell;
		String aKey="";
		//get sizes
		System.out.print("Enter size of hash table: ");
		size = getInt();
		System.out.print("Enter initial number of items: ");
		n = getInt();
		keysPerCell = 10;
		// make table
		HashTable theHashTable = new HashTable(size);
		for(int j=0; j<n; j++) // insert data
		{
			aKey="";
			for(int times=0;times<keysPerCell;times++)//Generate individual characters
			{
				int dum =(int)(((java.lang.Math.random())*100)/4)+65;//This expression changes the character strings allowed to be generated
				aKey += (char)dum;
			}
			aDataItem = new DataItem(aKey);
			theHashTable.insert(aDataItem);
		}
		while(true) // interact with user
		{
			System.out.print("Enter first letter of ");
			System.out.print("show, insert, delete, or find: ");
			char choice = getChar();
			switch(choice)
			{
			case 's':
				theHashTable.displayTable();
				break;
			case 'i':
				System.out.print("Enter key value to insert: ");
				aKey = getString();
				aDataItem = new DataItem(aKey);
				theHashTable.insert(aDataItem);
				break;
			case 'd':
				System.out.print("Enter key value to delete: ");
				aKey = getString();
				theHashTable.delete(aKey);
				break;
			case 'f':
				System.out.print("Enter key value to find: ");
				aKey = getString();
				aDataItem = theHashTable.find(aKey);
				if(aDataItem != null)
				{
					System.out.println("Found " + aKey);
				}
				else
					System.out.println("Could not find" + aKey);
				break;
			default:
				System.out.print("Invalid entry\n");
			} // end switch
		} // end while
	} // end main()
	//--------------------------------------------------------------
	public static String getString() throws IOException
	{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String s = br.readLine();
		return s;
	}
	//--------------------------------------------------------------
	public static char getChar() throws IOException
	{
		String s = getString();
		return s.charAt(0);
	}
	//-------------------------------------------------------------
	public static int getInt() throws IOException
	{
		String s = getString();
		return Integer.parseInt(s);
	}
	//--------------------------------------------------------------
} // end class HashTableApp
////////////////////////////////////////////////////////////////
