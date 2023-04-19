def linear(arr, n):      #O(n)
	
	if(arr[0] >= arr[1]):
		return 1 
	if(arr[-1] >= arr[-2]):
		return n
	for i in range(1, n-1):
		if(arr[i] >= arr[i-1] and arr[i] >= arr[i+1]):
			return(i+1)

def divide(arr, n):		#O(log n)
	if(arr[0] >= arr[1]):
		return 1 
	if(arr[-1] >= arr[-2]):
		return n
	l = 1
	u = n - 1
	while(l <= u):
		mid = l + (u - l) // 2
		if(arr[mid] < arr[mid - 1]):
			u = mid - 1
		elif(arr[mid] < arr[mid + 1]):
			l = mid + 1
		else:
			return(mid + 1)	

if __name__ == '__main__':
	n = int(input('Enter the no of elements: '))
	print('Enter {} elements: '.format(n), end = '')
	arr = list(map(int, input().split(' ')))
	pos = divide(arr, n)
	print(arr[pos - 1])
