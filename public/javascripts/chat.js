var app = angular.module('chatApp', ['ngMaterial']);

app.config(function ($mdThemingProvider) {
    $mdThemingProvider.theme('default')
        .primaryPalette('indigo')
        .accentPalette('red');
});

app.controller('chatController', function ($scope, $sce) {
    $scope.messages = [];
    $scope.trust = $sce.trustAsHtml;

    var exampleSocket = new WebSocket('ws://localhost:9000/chatSocket');

    exampleSocket.onmessage = function (event) {
        var jsonData = JSON.parse(event.data); //parse string to JSON
        jsonData.time = new Date()
            .toLocaleTimeString(); //returns a time stream
        $scope.messages.push(jsonData); //appending to the array
        $scope.$apply(); //
        console.log(jsonData);
    };
    $scope.sendMessage = function () {
        exampleSocket.send($scope.userMessage); //input field and user variable are bound together
        $scope.userMessage = '';
    };
});