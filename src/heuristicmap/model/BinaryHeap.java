package heuristicmap.model;

import java.util.Arrays;

/*
 * Specialized for checking
 */

public class BinaryHeap{

	int currCap = 10;
	int currSize;
	int index;
	Vertex[] array;

	public BinaryHeap(){
		array = new Vertex[currCap];
		currSize = 0;
		array[0] = null;
		index = 1;
	}

	public void insert(Vertex nodeIn){
		if(currSize > currCap){
			array = Arrays.copyOf(array, array.length * 2);
		}
		if(currSize == 0){
			array[index] = nodeIn;
			currSize++;
			return;
		}
		currSize++;
		index = currSize;
		array[index] = nodeIn;

		bubbleUp();
	}

	public Vertex pop(){
		Vertex target = array[1];
		remove();
		return target;
	}

	public void remove(){
		array[1] = array[currSize];
		array[currSize] = null;
		currSize--;

		bubbleDown();
	}

	public void bubbleUp(){
		index = this.currSize;

		while(index > 1 && array[index/2].getDistance() > array[index].getDistance()){
			swap(index, index);
			index = index/2;
		}
	}

	public void bubbleDown(){
		int index = 1;

		while(index * 2 <= currSize){
			int small = index * 2;

			if(index * 2 + 1 <= currSize
					&& array[index * 2].getDistance() > array[index * 2 + 1].getDistance()){
				small = index * 2 + 1;
			}

			if(array[index].getDistance() < array[small].getDistance()){
				swap(index, small);
			}
			else{
				break;
			}

			index = small;
		}
	}

	public Vertex getParent(int i){
		return array[i/2];
	}

	public void swap(int a, int b){
		Vertex temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}
}