package heuristicmap.model;

import java.util.Arrays;
import java.util.HashMap;

import heuristicmap.model.Vertex;

public class MultiFringe{
	HashMap<Integer, Vertex>[] hmap;
	int[] currSize;

	public MultiFringe(){
		currSize = new int[5];
		Arrays.fill(currSize, 0);
		hmap = new HashMap[5];
		for(int i = 0; i < 5; i++)
			hmap[i] = new HashMap<Integer, Vertex>();
	}

	public void add(int i, Vertex v){
		currSize[i]++;
		hmap[i].put(currSize[i], v);
		bubbleUp(i);
	}

	public void remove(int i){
		hmap[i].remove(1);
		hmap[i].put(1, hmap[i].get(currSize[i]));
		hmap[i].remove(currSize[i]);
		currSize[i]--;

		bubbleDown(i);
	}

	public void bubbleDown(int i){
		int index = 1;
		while(index * 2 <= currSize[i]){
			int small = index * 2;

			if(index * 2 + 1 <= currSize[i]
					&& hmap[i].get(index * 2).getFVal(i) > hmap[i].get(index * 2 + 1).getFVal(i)){
				small = index * 2 + 1;
			}

			if(hmap[i].get(index).getFVal(i) > hmap[i].get(small).getFVal(i)){
				swap(index, small, i);
			}
			else{
				break;
			}

			index = small;
		}
	}

	public void bubbleUp(int i){
		int index = currSize[i];

		while(index > 1 && hmap[i].get(index/2).getFVal(i) > hmap[i].get(index).getFVal(i)){
			swap(index/2, index, i);
			index = index/2;
		}
	}

	public void swap(int a, int b, int i){
		Vertex temp = hmap[i].get(a);
		hmap[i].put(a, hmap[i].get(b));
		hmap[i].put(b, temp);
	}
}
