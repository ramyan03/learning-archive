# int -> -267, 767
# float -> 27.0, -9.7
# string -> "string", 'string"'
# bool -> true, false

# end = '\n'

hello = 'i'
world = hello
hello = 'j' # only changes hello, not world

name = input('Name: ')
age = input('Age: ')

x = 6 # dynamically typed -> at runtime
y = 2
result = x//y

print(result)

# % -> mod

# int(num) -> Changes to int. When we use input, it assumes str, so use int() for math calculations using input

# .upper() is a method that changes str to upper case, similar to .lower(), .capitalize(), .count(), etc. Can chain them together

# str multiplcation repeats str by an int. Can concatenate str as well

# == is LS,RS check. !=, <=, =>, >, < are the conditional checks etc. For strings, can compare using ASCII code (using ord) -> a is greater than Z, but b is greater than a

# not, and , or is the order of operations

# if, elif (only after if, and before else), else

# lists [] -> ordered, can have different types. methods include .pop, .append, .extend. Lists are mutable (reference). : is to copy

# tuples () -> immutable list

# for loop is defined loop -> for i in range(10): iterates until 10, for i in range(1,10): interates from 1 to 10, for i in range (10, -1, -1): iterates from 10 to -1 at rate 1

# enumerate is another way to for loop 

# slice operator is slice of tuple, list, etc. -> sliced = [start, stop, step] -> x[0:4:2] means start at 0, end at 4, step by 2. We can also do [2:4] to remove step (default 1), or [::-1] reverse list, or [:4] is everything up to 4, or [2:] is everything from 2 onwards

# set {} is unique unordered collection. Can remove (error if not in set), or in (check if in set). Extremely fast for lookups, additions, removals

# dictionary {:} is a key value pair. Can also do for loops to look for each key, each value, etc

# comprehensions is a one line initialization of a collection, list, tuple, etc.

# raise exceptions, try and except 

# *args handles an arbitrary number of positional (non-keyword) arguments, which are collected into a tuple inside the function. **kwargs handles an arbitrary number of keyword (named) arguments (e.g., key=value), which are collected into a dictionary inside the function

# lambda is a one line anonymous function

# map and filter. Map is taking all elements and map to lambda function. Filter is checking if lists match a condition (can be lambda)

# can have multiple assignments -> n, m = 0, 1

# none is null. Also no ++, just +=. And, Or instead of && and ||

# / is decimal division, // is integer division (round down unless using int())

# math -> math.fmod, .floor, .ceil, .sqrt, .pow

# float("inf") or negative infinity

# zip is for iterating through multiple arrays simultaneously by combining both arrays into array of pairs then unpack them

# sort, sort(reverse=true), reverse, sort(key = lambda x: len(x)) is sorting by length

# create empty 2D array by doing arr = [[0]*4 for i in range(4)]

# can modify string by += but this will create new string. String can be converted to integers. Adding integers returns integer. Adding strings appends together

# "".join deimitor will join a list of strings

# queues are double ended so we can pop and push from both sides -> appendleft(), popleft(), append(), pop()

# use -> 1 in set to check if 1 is in hashset

# hashmaps -> dict using {} Can check if key exists in map. Add pairs in map by using commas and adding : between key and value. Can do for key in myMap, for val in myMap.values(), for key,val in myMap.items

# tuples used for keys for hash maps and hash sets. Lists can't be keys

# heaps -> min heaps. Index 0. Can heappop, heapppush. Multiply by negative 1 when pushing then multiply by -1 to reverse order

### DJANGO

# te