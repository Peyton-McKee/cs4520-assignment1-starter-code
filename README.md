### Project 5

I built off of project 1 starter code so its the same repo `https://github.com/Peyton-McKee/cs4520-assignment1-starter-code`

I first transitioned the LoginView to use a compose with two textfields and a button laid out in a column within a surface. I had to create a viewModel for handling the names through MutableLiveData, however there were some weird things going on so a TA recommended using MutableFlowState instead. 

Once I got the view working with the new viewModel I deleted the old views and fragments and started working on making the product list

I encountered a really weird error where i couldnt use the way i was making my network requests in the last assignment with compose otherwise it would throw a really weird internal error. So i talked with a TA to fix that by using viewModelScope instead of withContext and calling the fetch products on initalization of the viewmodel. 

I then created a view for the individual products with a little bit of modifiers and alignments to make it look nice

I also made a list view that iterated through the list of products in a lazy column and display the items based on whether they were equipment or food. 

I then started working on the background worker by creating a singleton repository object and moving my fetch products logic into there along with my add to database logic per a TA recommendation. 

I also created the APIWorkRequest for fetching the data from the random api instead of the page one. 

I then created a singleton object for my work manager that has some constraints like enforcing network connection and not requiring the battery to be plugged in to call a periodic work job every hour per assignment specification. 

I then called that function from the viewmodel after clearing any currently enqued jobs in order to not flood the system with background workers.
