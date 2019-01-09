==============
Program Author
==============

Will Mahler

=======
Purpose
=======

This program allows a user to construct a list of books via their own input for the books' authors, titles, and years of publication. The book details they provide are used to dynamically allocate book structures, which are then added to list structures in the form of nodes of a linked list.

The user's linked list of books is sorted alphabetically by title by default. The user may also choose to remove an author from the list of books (which is only done in a copy of their original list). After which, the user's books are sorted in descending order by year and displayed immediately after their original list, for comparison purposes.

After the above is complete, the program frees all dynamically allocated memory and terminates.

====================
List of Source Files
====================

assignment3.c

===================
Compilation Command
===================

gcc -Wall -o assignment3 -std=c99 assignment3.c

==================================
Launching & Operating Instructions
==================================

When the program is run: 
	- the user is continually prompted for input for a book's title, author, and year, as long as the user does not input "end" as the title
	- once the user inputs "end" as the title, the book list (sorted alphabetically by title) containing their books is printed
	- the user is then asked to enter the name of an author whose books they'd like to remove from a copied list
	- the original list and the modified list (which may not be modified if no books were found by the author provided) are then printed
	- the original list is then copied into a new list, sorted by descending order of year of publication
	- this copied year-sorted list is then printed out right after printing the original list
	- finally, all dynamically allocated memory is freed and the program terminates

