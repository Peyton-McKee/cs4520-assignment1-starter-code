### Project 4

I built off of project 1 starter code so its the same repo `https://github.com/Peyton-McKee/cs4520-assignment1-starter-code`

I first fixed the comments made initially by removing nested linear layouts and having constraint layouts for my product item. I also moved strings to the strings file. I then created the response api and added the decoding for that ignoring any messages that were missing data. 

Then I created a room database to store the items in with an upsert function to not duplicate values and avoid conflicting primary keys. 

I then did pagination by creating a scroll listener and loading data whenever I got to the end of the recycler view. I also filtered out any duplicate data in the adapter so that it only had unique items which I believe there are 90 of. 
