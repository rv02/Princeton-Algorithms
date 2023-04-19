# Python3 program to count number of ways any 
# given integer x can be expressed as n-th 
# power of unique natural numbers. 

# Function to calculate and return the 
# power of any given number 
def power(num, n): 

	if(n == 0): 
		return 1
	elif(n % 2 == 0): 
		return power(num, n // 2) * power(num, n // 2) 
	else: 
		return num * power(num, n // 2) * power(num, n // 2) 

# Function to check power representations recursively 
def checkRecursive(x, n, curr_num=1, curr_sum=0): 

	# Initialize number of ways to express 
	# x as n-th powers of different natural 
	# numbers 
	results = 0

	# Calling power of 'i' raised to 'n' 
	p = power(curr_num, n) 
	while(p + curr_sum < x): 

		# Recursively check all greater values of i 
		results += checkRecursive(x, n, curr_num + 1, p + curr_sum) 
		curr_num = curr_num + 1
		p = power(curr_num, n) 

	# If sum of powers is equal to x 
	# then increase the value of result. 
	if(p + curr_sum == x): 
		results = results + 1

	# Return the final result 
	return results 

# Driver Code. 
if __name__=='__main__': 
	x = 10
	n = 2
	print(checkRecursive(x, n)) 


