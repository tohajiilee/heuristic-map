package heuristicmap.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import heuristicmap.model.Algorithm;
import heuristicmap.model.Heuristic;
import heuristicmap.model.Map;

public class MultiIterator {
	public double weight1;
	public double weight2;
	public File [] mapList;
	
    // Lists to keep the data
    public double time;
    public double pathlength;
    public double nodeexpanded;
    public double memreqs;

    // Averages
    public double avgTime;
    public double avgPath;
    public double avgNode;
    public double avgMem;
	
	public MultiIterator(File [] mapList, double weight1, double weight2){
		this.mapList = mapList;
		this.weight1 = weight1;
		this.weight2 = weight2;
	}
	
	public void loadMap(){
		
	}
	
	public void saveMap(){
		
	}
	
	public void clearDateLists(){
		
	}
	
	public void runAll(){
		
	}
	
	public void calculateAverage(){
		
	}
	
	public void totalAverage(){
		
	}
	
	public void algo1(){
		
	}
	
	public void algo2(){
		
	}
	
	public void algo3(){
		
	}
	
	public void algo4(){
		
	}
	
	public void algo5(){
		
	}
	
	public void algo6(){
		
	}
	
	public void algo7(){
		
	}
	
	public void algo8(){
		
	}
	
	public void algo9(){
		
	}
	
	public void algo10(){
		
	}
	
	
}
