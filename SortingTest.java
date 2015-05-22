import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class SortingTest
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			boolean isRandom = false;	// 입력받은 배열이 난수인가 아닌가?
			int[] value;	// 입력 받을 숫자들의 배열
			String nums = br.readLine();	// 첫 줄을 입력 받음
			if (nums.charAt(0) == 'r')
			{
				// 난수일 경우
				isRandom = true;	// 난수임을 표시

				String[] nums_arg = nums.split(" ");

				int numsize = Integer.parseInt(nums_arg[1]);	// 총 갯수
				int rminimum = Integer.parseInt(nums_arg[2]);	// 최소값
				int rmaximum = Integer.parseInt(nums_arg[3]);	// 최대값

				Random rand = new Random();	// 난수 인스턴스를 생성한다.

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 각각의 배열에 난수를 생성하여 대입
					value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
			}
			else
			{
				// 난수가 아닐 경우
				int numsize = Integer.parseInt(nums);

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 한줄씩 입력받아 배열원소로 대입
					value[i] = Integer.parseInt(br.readLine());
			}

			// 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.
			while (true)
			{
				int[] newvalue = (int[])value.clone();	// 원래 값의 보호를 위해 복사본을 생성한다.

				String command = br.readLine();

				long t = System.currentTimeMillis();
				switch (command.charAt(0))
				{ 
					case 'B':	// Bubble Sort
						newvalue = DoBubbleSort(newvalue);
						break;
					case 'I':	// Insertion Sort
						newvalue = DoInsertionSort(newvalue);
						break;
					case 'H':	// Heap Sort
						newvalue = DoHeapSort(newvalue);
						break;
					case 'M':	// Merge Sort
						newvalue = DoMergeSort(newvalue);
						break;
					case 'Q':	// Quick Sort
						newvalue = DoQuickSort(newvalue);
						break;
					case 'R':	// Radix Sort
						newvalue = DoRadixSort(newvalue);
						break;
					case 'X':
						return;	// 프로그램을 종료한다.
					default:
						throw new IOException("잘못된 정렬 방법을 입력했습니다.");
				}
				if (isRandom)
				{
					// 난수일 경우 수행시간을 출력한다.
					System.out.println((System.currentTimeMillis() - t) + " ms");
				}
				else
				{
					// 난수가 아닐 경우 정렬된 결과값을 출력한다.
					for (int i = 0; i < newvalue.length; i++)
					{
						System.out.println(newvalue[i]);
					}
				}

			}
		}
		catch (IOException e)
		{
			System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoBubbleSort(int[] value)
	{
		for (int i = 0; i < value.length; i++){
			for (int j = value.length - 1; j > i; j--){
				if (value[j] < value[j - 1]){
					int temp = value[j];
					value[j] = value[j - 1];
					value[j - 1] = temp;
				}
			}
		}
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoInsertionSort(int[] value)
	{
		for (int i = 0; i < value.length; i++){
			for (int j = i; j > 0; j--){
				if (value[j] < value[j - 1]){
					int temp = value[j];
					value[j] = value[j - 1];
					value[j - 1] = temp;
				} else break;
			}
		}
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoHeapSort(int[] value)
	{
		for (int i = 0; i < value.length; i++){
			for (int k = i; k != 0 && value[(k - 1) / 2] < value[k]; k = (k - 1) / 2){
				int temp = value[k];
				value[k] = value[(k - 1) / 2];
				value[(k - 1) / 2] = temp;
			}
		} //make heap
		for (int i = value.length - 1; i >= 0; i--){
			{
				int temp = value[i];
				value[i] = value[0];
				value[0] = temp;
			}
			for (int j = 0; ; ){
				if (2 * j + 2 < i && value[j] < value[2 * j + 2]
						&& value[2 * j + 2] > value[2 * j + 1]){
					int temp = value[j];
					value[j] = value[2 * j + 2];
					value[2 * j + 2] = temp;
					j = 2 * j + 2;
				} else if (2 * j + 1 < i && value[j] < value[2 * j + 1]){
					int temp = value[j];
					value[j] = value[2 * j + 1];
					value[j * 2 + 1] = temp;
					j = 2 * j + 1;
				} else {
					break;
				}
			}
		}
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoMergeSort(int[] value)
	{
		if (value.length == 1) {
			return (value);
		}
		int[] rValue = new int[value.length / 2];
		int[] lValue = new int[value.length - value.length / 2];
		int i;
		for (i = 0; i < value.length / 2; i++){
			rValue[i] = value[i];
		}
		for (int j = 0; i < value.length; i++, j++){
			lValue[j] = value[i];
		}
		rValue = DoMergeSort(rValue);
		lValue = DoMergeSort(lValue);
		for (int iR = 0, iL = 0; iR + iL < value.length; ) {
			if (iR < rValue.length && iL < lValue.length) {
				//compare both numbers
				if (rValue[iR] > lValue[iL]) {
					value[iR + iL] = lValue[iL];
					iL++;
				} else {
					value[iR + iL] = rValue[iR];
					iR++;
				}
			} else if (iR < rValue.length) {
				//concatenate rValue
				value[iR + iL] = rValue[iR];
				iR++;
			} else if (iL < lValue.length){
				//concatenate lValue
				value[iR + iL] = lValue[iL];
				iL++;
			}
		}
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoQuickSort(int[] value)
	{
		/*
		ArrayList<Integer> integerArrayList = new ArrayList<Integer>();
		for (int i = 0 ; i < value.length ; i++){
			integerArrayList.add(value[i]);
		}
		integerArrayList = QuickSort(integerArrayList);
		for (int i = 0 ; i < integerArrayList.size() ; i++){
			value[i] = integerArrayList.get(i);
		}
		*/
		QuickSort(value, 0, value.length - 1);
		return (value);
	}
	
	private static void QuickSort(int[] value, int first, int last){
		while (first < last){
			int pivotIndex = value[first] > value[last]
					? value[first] > value[(first + last) / 2] ? (first + last) / 2 : first
					: value[(first + last) / 2] > value[last] ? last : (first + last) / 2 ;
			int c = 0;
			{
				int temp = value[pivotIndex];
				value[pivotIndex] = value[first];
				value[first] = temp;
			}
			for (int i = first + 1; i <= last ; i++){
				if (value[i] < value[first]){
					c++;
					int temp = value[first + c];
					value[first + c] = value[i];
					value[i] = temp;
				}
			}
			{
				int temp = value[first + c];
				value[first + c] = value[first];
				value[first] = temp;
			}
			QuickSort(value, first, first + c - 1);
			first = first + c + 1;
		}
		return;
		/*
		if (last <= first) {
			return;
		}
		int pivotIndex = value[first] > value[last]
				? value[first] > value[(first + last) / 2] ? (first + last) / 2 : first
				: value[(first + last) / 2] > value[last] ? last : (first + last) / 2 ;
		int c = 0;
		{
			int temp = value[pivotIndex];
			value[pivotIndex] = value[first];
			value[first] = temp;
		}
		for (int i = first + 1; i <= last ; i++){
			if (value[i] < value[first]){
				c++;
				int temp = value[first + c];
				value[first + c] = value[i];
				value[i] = temp;
			}
		}
		{
			int temp = value[first + c];
			value[first + c] = value[first];
			value[first] = temp;
		}
		QuickSort(value, first, first + c - 1);
		QuickSort(value, first + c + 1, last);
		return;
		*/
	}

	/*
	private static ArrayList<Integer> QuickSort(ArrayList<Integer> integerArrayList){
		if (integerArrayList.size() <= 1) {
			return integerArrayList;
		} else {
			int pivotIndex = integerArrayList.size() / 2;
			Integer pivot = integerArrayList.get(pivotIndex);
			ArrayList<Integer> left = new ArrayList<Integer>();
			ArrayList<Integer> right = new ArrayList<Integer>();
			for (int i = 0 ; i < integerArrayList.size() ; i++){
				if (i == pivotIndex){
					continue;
				} 
				if (integerArrayList.get(i) < pivot){
					left.add(integerArrayList.get(i));
				} else {
					right.add(integerArrayList.get(i));
				}
			}
			left = QuickSort(left);
			right = QuickSort(right);
			integerArrayList = new ArrayList<Integer>();
			for (int i = 0 ; i < left.size() ; i++){
				integerArrayList.add(left.get(i));
			}
			integerArrayList.add(pivot);
			for (int i = 0 ; i < right.size() ; i++){
				integerArrayList.add(right.get(i));
			}
		}
		return integerArrayList;
	}
	*/
	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoRadixSort(int[] value)
	{
		/*
		int len = value.length;
		int[] iQueue = new int[10];
		int[][] queue= new int[10][len];
		for (int d = 0 ;  ; d++){
			for (int i = 0 ; i < len ; i++){
				int digit = value[i] / (int)Math.pow(10, d) % 10;
				queue[digit][iQueue[digit]++] = value[i];
			}
			if (iQueue[0] == len){
				break;
			}
			for (int i = 0, c = 0 ; i < 10 ; i++){
				for (int j = 0 ; j < iQueue[i] ; j++) {
					value[c++] = queue[i][j];
				}
				iQueue[i] = 0;
			}
		}
		*/
		/*
		Queue[] queue = new Queue[65536];
		for (int i = 0 ; i < 65536 ; i++){
			queue[i] = new Queue();
		}
		for (int d = 0 ;  ; d++){
			for (int i = 0 ; i < value.length ; i++){
				int r = value[i] / (int)Math.pow(65536, d) % 65536;
				queue[r].push(value[i]);
			}
			if (queue[0].getSize() == value.length){
				break;
			}
			for (int i = 0, c = 0 ; i < 65536 ; i++){
				while (!queue[i].isEmpty()){
					value[c++] = queue[i].pop();
				}
			}
		}
		return (value);
		*/
		int max = value[0] > 0 ? value[0] : -value[0];
		for (int a : value){
			if (max < (a > 0 ? a : -a))
				max = (a > 0 ? a : -a);
		}
		final int numberOfVertex = 1000;
		int[] vertexNumber = new int[numberOfVertex * 2 - 1];
		int[] vertexIndex = new int[numberOfVertex * 2 - 1];
		int[] temp_value = new int[value.length];
		for (int d = 0; (Math.pow(numberOfVertex, d) < max) ; d++){
			for (int i = 0; i < numberOfVertex * 2 - 1; i++){
				vertexIndex[i] = 0;
				vertexNumber[i] = 0;
			}
			for (int i = 0; i < value.length; i++){
				int r = (value[i] / (int)Math.pow(numberOfVertex, d) % numberOfVertex)
						+ numberOfVertex - 1;
				vertexNumber[r]++;
			}
			for (int i = 1; i < numberOfVertex * 2 - 1; i++){
				vertexIndex[i] += vertexNumber[i - 1] + vertexIndex[i - 1];
			}
			//count
			for (int i = 0; i < value.length; i++){
				int r = (value[i] / (int)Math.pow(numberOfVertex, d) % numberOfVertex)
						+ numberOfVertex - 1;
				temp_value[vertexIndex[r]++] = value[i];
			}
			value = temp_value.clone();
			//sorting
		}
		int negativeIndex = 0;
		int positiveIndex = 0;
		temp_value = new int[value.length];
		for (int a : value){
			if (a < 0)
				positiveIndex++;
		}
		for (int a : value){
			if (a < 0)
				temp_value[negativeIndex++] = a;
			else
				temp_value[positiveIndex++] = a;
		}
		value = temp_value.clone();
		return value;
	}
}
/*
class Node {
	int value;
	Node next = null;
}
class Queue {
	Node tail;
	int size;
	Queue() {
		tail = null;
		size = 0;
	}
	void push(int value) {
		Node newNode = new Node();
		newNode.value = value;
		if (isEmpty()) {
			newNode.next = newNode;
		} else {
			newNode.next = tail.next;
			tail.next = newNode;
		}
		tail = newNode;
		size++;
	}
	int pop() {
		Node first = tail.next;
		if (first == tail) {
			tail = null;
		} else {
			tail.next = first.next;
		}
		size--;
		return first.value;
	}
	boolean isEmpty() {
		return tail == null;
	}
	int getSize(){
		return size;
	}
}
*/