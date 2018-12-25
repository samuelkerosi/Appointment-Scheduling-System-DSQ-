// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');

// The Firebase Admin SDK to access the Firebase Realtime Database.
const admin = require('firebase-admin');
admin.initializeApp();

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
//exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
//});


exports.testNotification = functions.database.instance('test-project-c905c').ref('/Users/User/FirstName').onWrite((snapshot, context) => {

    // Get the list of device notification token.
    const getDeviceTokensPromise = admin.database().ref('/Users/User/NotificationToken').once('value');

    // The snapshot to the user's tokens.
    let tokenSnapshot;

    // The array containing all the user's tokens.
    let tokens;

    return Promise.all([getDeviceTokensPromise]).then(results => {
        tokenSnapshot = results[0];
        var registrationToken = tokenSnapshot.val();

        // Check if there are any device tokens.
        /*if (!tokensSnapshot.hasChildren()) {
            return console.log('There are no notification tokens to send to.');
        }
        console.log('There are', tokensSnapshot.numChildren(), 'tokens to send notifications to.');*/

        // Notification details.
        const payload = {
            notification: {
            title: 'Database triggered notification!',
            body: "Something was changed!"
            }
        };

        // Listing all tokens as an array.
        console.log("registrationToken: " + registrationToken);
        //tokens = [tokenSnapshot.val()];
        //tokens = Object.keys(tokenSnapshot.val());
        // Send notifications to all tokens.
        return admin.messaging().sendToDevice(registrationToken, payload);
    }).then((response) => {
        // For each message check if there was an error.
        const tokensToRemove = [];
        response.results.forEach((result, index) => {
            const error = result.error;
            if (error) {
                console.error('Failure sending notification to', tokens[index], error);
                // Cleanup the tokens who are not registered anymore.
                if (error.code === 'messaging/invalid-registration-token' || error.code === 'messaging/registration-token-not-registered')
                {
                    tokensToRemove.push(tokenSnapshot.ref.child(tokens[index]).remove());
                }
            }
            else
            {
                console.log("No errors! Notification sent!");
            }
        });
    return Promise.all(tokensToRemove);
    });
});