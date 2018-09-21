import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;




public class PositionCalculation {

	public static List<Position> readStartOfDayPositions(){
		List<Position> listOfPositionsAtStartOfDay=new ArrayList<Position>();
		FileReader fr=null;
		BufferedReader br=null;
		try{
			fr=new FileReader("P:\\Saurabh\\Input_StartOfDay_Positions.txt");
			br=new BufferedReader(fr);
			String currentLine;
			int lineNumber=0;
			while((currentLine=br.readLine())!=null){
				lineNumber++;
				if(lineNumber>1){
					String[] values=currentLine.split(",");
					Position position=new Position();
					position.setInstrument(values[0]);
					position.setAccount(Integer.parseInt(values[1]));
					position.setAccountType(values[2].charAt(0));
					position.setQuantity(Long.parseLong(values[3]));
					listOfPositionsAtStartOfDay.add(position);
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			}catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return listOfPositionsAtStartOfDay;
	}
	
	public static List<Transaction> readTransactions(){
		List<Transaction> listOfTransations=new ArrayList<Transaction>();
		JSONParser parser = new JSONParser();
		try{
			JSONArray objectArray=(JSONArray) parser.parse(new FileReader("P:\\Saurabh\\Input_Transactions.json"));
			for(Object obj:objectArray){
				JSONObject object=(JSONObject)obj;
				Transaction transaction=new Transaction();
				transaction.setInstrument((String)object.get("Instrument"));
				transaction.setTransactionId((Long)object.get("TransactionId"));
				transaction.setTransactionQuantity((Long)object.get("TransactionQuantity"));
				transaction.setTransactionType((String)object.get("TransactionType"));
				listOfTransations.add(transaction);
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		return listOfTransations;
	}

	public static List<Position> calculatePositions(List<Position> listOfPositions , List<Transaction> listOfTransations){
		
		for(ListIterator<Transaction> titr=listOfTransations.listIterator(); titr.hasNext();){
			Transaction transaction=(Transaction)titr.next();
			String instrument=transaction.getInstrument();
			for(ListIterator<Position> pitr=listOfPositions.listIterator(); pitr.hasNext();){
				Position position=(Position)pitr.next();
				if(instrument.equals(position.getInstrument())){
					if(transaction.getTransactionType()=='B'){
						if(position.getAccountType()=='E'){
							position.setQuantity(position.getQuantity()+transaction.getTransactionQuantity());
							position.setDelta(position.getDelta()+transaction.getTransactionQuantity());
						}else if(position.getAccountType()=='I'){
							position.setQuantity(position.getQuantity()-transaction.getTransactionQuantity());
							position.setDelta(position.getDelta()-transaction.getTransactionQuantity());
						}
					}else if(transaction.getTransactionType()=='S'){
						if(position.getAccountType()=='E'){
							position.setQuantity(position.getQuantity()-transaction.getTransactionQuantity());
							position.setDelta(position.getDelta()-transaction.getTransactionQuantity());
						}
						else if(position.getAccountType()=='I'){
							position.setQuantity(position.getQuantity()+transaction.getTransactionQuantity());
							position.setDelta(position.getDelta()+transaction.getTransactionQuantity());
						}
					}
				}
			}
		}
		
		
		return listOfPositions;
	}
	
	public static void writeFinalPositions(List<Position> finalPositions){
		FileWriter fw=null;
		BufferedWriter bw=null;
		try{
			fw=new FileWriter("P:\\Saurabh\\EndOfDay_Positions.txt");
			bw=new BufferedWriter(fw);
			bw.write("Instrument,Account,AccountType,Quantity,Delta");
			bw.write(System.getProperty("line.separator"));
			for(ListIterator<Position> itr=finalPositions.listIterator(); itr.hasNext();){
				Position pos=(Position)itr.next();
				bw.write(pos.getInstrument()+","+pos.getAccount()+","+pos.getAccountType()+","+pos.getQuantity()+","+pos.getDelta());
				bw.write(System.getProperty("line.separator"));
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	
	public static void main(String[] args) {
		List<Position> listOfPositionsAtStartOfDay=readStartOfDayPositions();
		List<Transaction> listOfTransations=readTransactions();
		List<Position> finalPositions=calculatePositions( listOfPositionsAtStartOfDay , listOfTransations );
		writeFinalPositions(finalPositions);
		
	}

}
