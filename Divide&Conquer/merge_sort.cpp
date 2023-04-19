#include <iostream>
#include <vector>
#include <iterator>

void merge(std::vector<int> &vec, int l, int mid, int r)
{
    std::vector<int> v1, v2;
    std::vector<int>::const_iterator iter1, iter2;
    std::vector<int>::iterator k = vec.begin() + l; 
    v1.assign(vec.cbegin() + l, vec.cbegin() + mid + 1);
    v2.assign(vec.cbegin() + mid + 1, vec.cbegin() + r + 1);
    for(iter1 = v1.cbegin(), iter2 = v2.cbegin(); iter1 != v1.cend() && iter2 != v2.cend(); k++)
    {
        if(*iter1 < *iter2){
            *k = *iter1;
            iter1++;
        } else {
            *k = *iter2;
            iter2++;
        }    
    }
    while(iter1 != v1.cend()){
        *k = *iter1;
        k++;
        iter1++;
    }
    while(iter2 != v2.cend()){
        *k = *iter2;
        k++;
        iter2++;
    }
}

void mergeSort(std::vector<int> &v, int l, int r)
{
    if (r > l){
        int mid = (l + r) / 2;
        mergeSort(v, l, mid);
        mergeSort(v, mid + 1, r);
        merge(v, l, mid, r);
    }
    
}

int main()
{
    std::vector<int> v = {12, 11, 13, 5, 6, 7};
    mergeSort(v, 0, 5);
    for(auto i : v){
        std::cout << i << " ";
    }
    std::cout << "\n";
    return 0;
}