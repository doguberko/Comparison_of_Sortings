
public class SortingClass {

	void printArray(int arr[]) {
		int n = arr.length;
		for (int i = 0; i < n; ++i)
			System.out.print(" " + arr[i]);
		System.out.println();
	}

	// HEAP SORT
	void buildMaxHeap(int[] arr) {
		int temp, n = arr.length;

		for (int i = n / 2 - 1; i >= 0; i--) {
			maxHeapify(arr, n, i);
		}

		for (int i = n - 1; i >= 0; i--) {
			temp = arr[0];
			arr[0] = arr[i];
			arr[i] = temp;
			maxHeapify(arr, i, 0);
		}

	}

	void maxHeapify(int[] arr, int n, int i) {
		int left = i * 2 + 1;
		int right = i * 2 + 2;
		int largest, temp;

		if (left < n && arr[left] > arr[i]) {
			largest = left;
		} else {
			largest = i;
		}

		if (right < n && arr[right] > arr[largest]) {
			largest = right;
		}
		if (largest != i) {
			temp = arr[largest];
			arr[largest] = arr[i];
			arr[i] = temp;
			maxHeapify(arr, n, largest);
		}
	}

	// QUICK SORT
	void quickSort(int[] arr, int pivotType) {
		int temp;
		int as = arr.length;
		int rndm, first, mid, last;
		if (pivotType == 0) {
			// The pivot is last element
		} else if (pivotType == 1) {// FirstElement
			System.out.println(">> FirstElement:" + arr[0]);
			temp = arr[0];
			arr[0] = arr[as - 1];
			arr[as - 1] = temp;
		} else if (pivotType == 2) {// RandomElement
			rndm = (int) (Math.random() * as + 0);
			System.out.println(">> RandomElement:" + rndm);

			if (rndm != as - 1) {
				temp = arr[rndm];
				arr[rndm] = arr[as - 1];
				arr[as - 1] = temp;
			}
		} else if (pivotType == 3) {// MidOfFirstMidLastElement
			first = arr[0];
			last = arr[arr.length - 1];
			mid = arr[arr.length / 2];
			System.out.println("First:" + first + " Mid:" + mid + " Last:" + last);
			if ((mid < last && mid > first) || (mid > last && mid < first)) {
				arr[arr.length / 2] = arr[arr.length - 1];
				arr[arr.length - 1] = mid;
			} else if ((first < mid && first > last) || (first > mid && first < last)) {
				arr[0] = arr[arr.length - 1];
				arr[arr.length - 1] = first;
			}
			System.out.println(">> MidOfFirstMidLastElement:" + arr[arr.length - 1]);
		} else {
			System.out.println(" INVALID PIVOT TYPE !!");
		}

		quickSortProcessing(arr, 0, as - 1);
	}

	void quickSortProcessing(int[] arr, int p, int r) {
		if (p < r) {
			int q = partition(arr, p, r);
			quickSortProcessing(arr, p, q - 1);
			quickSortProcessing(arr, q + 1, r);
		}
	}

	int partition(int[] arr, int p, int r) {
		int x = arr[r];
		int i = p - 1;
		int temp;

		for (int j = p; j < r; j++) {
			if (arr[j] <= x) {
				i += 1;
				temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}

		temp = arr[i + 1];
		arr[i + 1] = arr[r];
		arr[r] = temp;
		return i + 1;
	}

	// DUAL PIVOT QUICK SORT
	// REFERENCE
	// https://codeblab.com/wp-content/uploads/2009/09/DualPivotQuicksort.pdf
	void dpqSort(int[] arr) {
		sort(arr, 0, arr.length);
	}

	void sort(int[] a, int fromIndex, int toIndex) {
		rangeCheck(a.length, fromIndex, toIndex);
		dualPivotQuicksort(a, fromIndex, toIndex - 1, 3);
	}

	void rangeCheck(int length, int fromIndex, int toIndex) {
		if (fromIndex > toIndex) {
			throw new IllegalArgumentException("fromIndex > toIndex");
		}
		if (fromIndex < 0) {
			throw new ArrayIndexOutOfBoundsException(fromIndex);
		}
		if (toIndex > length) {
			throw new ArrayIndexOutOfBoundsException(toIndex);
		}
	}

	void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	void dualPivotQuicksort(int[] a, int left, int right, int div) {
		int len = right - left;
		if (len < 27) { // insertion sort for tiny array
			for (int i = left + 1; i <= right; i++) {

				for (int j = i; j > left && a[j] < a[j - 1]; j--) {
					swap(a, j, j - 1);
				}
			}
			return;
		}
		int third = len / div;
		// "medians"
		int m1 = left + third;
		int m2 = right - third;
		if (m1 <= left) {
			m1 = left + 1;
		}
		if (m2 >= right) {
			m2 = right - 1;
		}
		if (a[m1] < a[m2]) {
			swap(a, m1, left);
			swap(a, m2, right);
		} else {
			swap(a, m1, right);
			swap(a, m2, left);
		}
		// pivots
		int pivot1 = a[left];
		int pivot2 = a[right];
		// pointers
		int less = left + 1;
		int great = right - 1;
		// sorting
		for (int k = less; k <= great; k++) {
			if (a[k] < pivot1) {
				swap(a, k, less++);
			} else if (a[k] > pivot2) {
				while (k < great && a[great] > pivot2) {
					great--;
				}
				swap(a, k, great--);
				if (a[k] < pivot1) {
					swap(a, k, less++);
				}
			}
		}
		// swaps
		int dist = great - less;
		if (dist < 13) {
			div++;
		}
		swap(a, less - 1, left);
		swap(a, great + 1, right);
		// subarrays
		dualPivotQuicksort(a, left, less - 2, div);
		dualPivotQuicksort(a, great + 2, right, div);

		// equal elements
		if (dist > len - 13 && pivot1 != pivot2) {
			for (int k = less; k <= great; k++) {
				if (a[k] == pivot1) {
					swap(a, k, less++);
				} else if (a[k] == pivot2) {
					swap(a, k, great--);
					if (a[k] == pivot1) {
						swap(a, k, less++);
					}
				}
			}
		}
		// subarray
		if (pivot1 < pivot2) {
			dualPivotQuicksort(a, less, great, div);
		}
	}

	// INTRO SORT
	void introSort(int[] arr) {
		int MaxDepth = (int) (Math.log(arr.length) * 2);
		introSortProcessing(arr, MaxDepth, 0, arr.length - 1);
	}

	void introSortProcessing(int[] arr, int MaxDepth, int p, int r) {
		int n = arr.length;
		int isp = partition(arr, 0, arr.length - 1);

		if (n <= 1) {
			return;
		} else if (MaxDepth == 0) {
			buildMaxHeap(arr);
		} else {
			introSortProcessing(arr, MaxDepth - 1, 0, isp - 1);
			introSortProcessing(arr, MaxDepth - 1, isp, arr.length - 1);
		}
	}

}
