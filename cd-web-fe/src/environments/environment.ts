// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  apiUrl: 'http://localhost:8081/api/v1',
  firebase: {
    apiKey: "AIzaSyD93GOBqxi37SxFNUMU5Nm8pGzFgYEEBTM",
    authDomain: "peakyblinders-12eb8.firebaseapp.com",
    projectId: "peakyblinders-12eb8",
    storageBucket: "peakyblinders-12eb8.appspot.com",
    messagingSenderId: "817951574350",
    appId: "1:817951574350:web:184c1d78d092a822fdb61f"
  }
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
