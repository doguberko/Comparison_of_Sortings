
public class Test {

	public static void main(String[] args) {
		SortingClass sc = new SortingClass();
		int[] arr = new int[100000];
		int rndm;

		for (int i = 0; i < arr.length; i++) {
			// rndm = (int) (Math.random() * 999 + 0);
			arr[i] = 100000 - i;
		}

		long startTime = System.currentTimeMillis();

		// sc.buildMaxHeap(arr);
		// sc.quickSort(arr, 1);
		// sc.quickSort(arr, 2);
		// sc.quickSort(arr, 3);
		// sc.dpqSort(arr);
		sc.introSort(arr);

		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("\n>> Time:" + estimatedTime);
	}

}
