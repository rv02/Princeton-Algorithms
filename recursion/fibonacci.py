def fib(n):
    '''Returns fibonacci of nth and (n-1)th term'''
    if n == 0:
        print('1')
        return(1, 0)
    else:
        (a, b) = fib(n-1)
        print(a+b)
        return(a+b, a)

term = int(input('Enter the no. of terms in series: '))
fib(term-1)