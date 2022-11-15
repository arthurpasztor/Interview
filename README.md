### Interview Application Explanation
I created this application that fetches images from the Unsplash API, and does the following:
- On first start, the user sees a single image in a list coming from the API.
- When the user clicks a button, a new random image from the API is appended to the bottom of the list.
- When the user taps on an image, the image details are displayed on another fragment.
- When the app is reopened, the list from the previous session is displayed, all displayed images being stored in the database.

I used MVVM from Jetpack, also Room, Coroutines, Flow, Livedata, Retrofit, Glide, Parcelable
As an Architecture, I implemented the following dependency pattern:
View (Activity, Fragment) -> ViewModel -> Repository -> API/Room






#### API documentation
The UnSplash API requires a key. It can be obtained through this [url](https://unsplash.com/documentation#creating-a-developer-account) by creating a developer account.

The full documentation of the API can be found [here](https://unsplash.com/documentation).
The random image endpoint documentation can also be found [here](https://unsplash.com/documentation#get-a-random-photo)
