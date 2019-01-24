(function () {
    'use strict';

    angular
		.module('app')
		.controller('loginController', loginController);

    loginController.$inject = ['$scope','$window','$localStorage','$location', '$stateParams'];
        function loginController( $scope, $window, $localStorage, $location, $stateParams) {
            $scope.token = $stateParams.token;

            $scope.init = function(){
            }


        }
})();