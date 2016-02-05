# sunrisedemo
Sunrise Demo

Notes to the reviewers:
------

- What this program does:

   - Establishes two RecyclerView, top recycler view is managed by grid view manager, it holds the calendar 
     data.
   - Bottom  recycler view is used to display appointment data.

- Issues
   Where do I start!
   
   - I really have not had time to write unit test, mock test or espresso test.
   - Code uses hardcoded string, dimensions.
   - Right now calendar data is dummy data its not dowloaded using any kind of sync service.
   - Big thing I really wanted to do was scroll to appt when user clicks on date, I will try to work on this on
     superbowl sunday!
   - I certainly couldn't get to fetch data from network feature you had requested....


- If you folks are interested in looking at my sample Android code you can also see:
   https://github.com/snijsure/TwitterApiSampleV2. There I have implemented pull down to refresh,
   infinite scroll features.

- A request, I couldn't for life of me figure out how to get swipe gestures to work on recycler view managed
   by girdlayout manager. Hence the ugly next/prev buttons. If you know how to do that would love to hear from you.

(NOTE:
- I wanted to get something out to you folks before too long and my work (&life) schedule hasn't allowed me
  huge amount of time to implement/cleanup stuff.
)

 -Subodh Nijsure ( subodh.nijsure@gmail.com )
