// A program to display max and min elements in an array
#include <iostream>
#include <array>

int max_elem(std::array<int, 6> &a, int index, int size)
{
    int max;
    if (index >= size - 2){
        if (a[index] > a[index + 1])
            return a[index];
        else
            return a[index + 1];
    }
    max = max_elem(a, index + 1, size);
    if (max > a[index])
        return max;
    else
        return a[index];
    
}

int min_elem(std::array<int, 6> &a, int index, int size)
{
    int min;
    if (index >= size - 2)
    {
        if (a[index] < a[index + 1])
            return a[index];
        else
            return a[index + 1];
    }
    min = min_elem(a, index + 1, size);
    if (min < a[index])
        return min;
    else
        return a[index];
}

int main()
{
    std::array<int, 6> arr = {3, 5, 67, 23, 0, 45};
    std::cout << max_elem(arr, 0, 6) << "\n";
    std::cout << min_elem(arr, 0, 6) << "\n";
    return 0;
}