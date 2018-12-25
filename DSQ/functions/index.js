// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');

// The Firebase Admin SDK to access the Firebase Realtime Database.
const admin = require('firebase-admin');
admin.initializeApp();


exports.appointmentChangeNotification = functions.database.instance('dynamic-scheduling-system').ref('/Appointments/{AppointmentId}/{changedAttribute}').onUpdate((change, context) => {

    const originalValue = change.before.val();
    const newValue = change.after.val();
    //const attributeName = change.data.key();
    //const attributeName = ;
    const appointmentId = context.params.AppointmentId;

    console.log(originalValue + ' changed to ' + newValue);

    //Get appointment object
    admin.database().ref(`/Appointments/${appointmentId}`).on("value", function(snapshot) {
        const appointmentObj = snapshot.val();

        const appointmentTitle = appointmentObj.title;
        const visitorId = appointmentObj.visitorId;

        // Get the user's device notification token
        const getDeviceTokensPromise = admin.database().ref(`/Users/${visitorId}/notificationToken`).once('value');

        // The snapshot to the user's tokens.
        let tokensSnapshot;

        // The array containing all the user's tokens.
        let tokens;

        return Promise.all([getDeviceTokensPromise]).then(results => {
            tokensSnapshot = results[0];
            const notificationToken = tokensSnapshot.val();
            console.log("notificationToken: " + notificationToken);

            // Notification details.
            const payload = {
                notification: {
                    title: appointmentTitle,
                    body: 'Appointment details modified: ' + newValue
                },
                data: {
                    userId: visitorId
                }
            };

            // Send notifications to all tokens.
            return admin.messaging().sendToDevice(notificationToken, payload);
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
                        tokensToRemove.push(tokensSnapshot.ref.child(tokens[index]).remove());
                    }
                }
                else
                {
                    console.log("No errors! Notification sent!");
                }
            });
        return Promise.all(tokensToRemove);
        });
      }, function (errorObject) {
        console.log("The read failed: " + errorObject.code);
      });
});