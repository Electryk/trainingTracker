angular.module('trainingTrackerApp', ['ui.router', 'base64'])

    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider

            //starter screen
            .state('starter', {
                url: "/starter",
                templateUrl: "templates/starter.html",
                controller: "starterCtrl",
                onEnter: function ($state, auth) {
                    if (auth.isAuthenticated()) {
                        $state.go('home');
                    }
                }
            })
            //sign up screen
            .state('signUp', {
                url: "/signUp",
                templateUrl: "templates/signUp.html",
                controller: "signUpCtrl",
                onEnter: function ($state, auth) {
                    if (auth.isAuthenticated()) {
                        $state.go('home');
                    }
                }
            })
            //home screen
            .state('home', {
                url: "/home",
                templateUrl: "templates/home.html",
                controller: "homeCtrl",
                onEnter: function ($state, auth) {
                    if (!auth.isAuthenticated()) {
                        $state.go('starter');
                    }
                }
            })
            //add exercise screen
            .state('addExercise', {
                url: "/addExercise",
                templateUrl: "templates/addExercise.html",
                controller: "addExerciseCtrl",
                onEnter: function ($state, auth) {
                    if (!auth.isAuthenticated()) {
                        $state.go('starter');
                    }
                }
            })
            //exercise history screen
            .state('exerciseHistory', {
                url: "/exerciseHistory",
                templateUrl: "templates/exerciseHistory.html",
                controller: "exerciseHistoryCtrl",
                onEnter: function ($state, auth) {
                    if (!auth.isAuthenticated()) {
                        $state.go('starter');
                    }
                }
            });

        $urlRouterProvider.otherwise('starter');
    });
