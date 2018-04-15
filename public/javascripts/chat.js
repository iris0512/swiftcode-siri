var app = angular.module('chatApp', ['ngMaterial']);

app.config(function ($mdThemingProvider) {
    $mdThemingProvider.theme('default')
        .primaryPalette('indigo')
        .accentPalette('orange');
});

app.controller('chatController', function ($scope) {
    $scope.messages = [
        {
            'sender': 'USER',
            'text': 'Hello'
				},
        {
            'sender': 'BOT',
            'text': 'What can I do for you?'
				},
        {
            'sender': 'USER',
            'text': 'Help me search'
				},
        {
            'sender': 'BOT',
            'text': 'What would you like me to search for?'
				}
			];

});