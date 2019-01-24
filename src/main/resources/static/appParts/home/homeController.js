(function () {
    'use strict';

    angular
		.module('app')
		.controller('homeController', homeController);

    homeController.$inject = ['$scope','$window','$localStorage','$location', '$stateParams'];
        function homeController( $scope, $window, $localStorage, $location, $stateParams) {
        	var hc=this;
            $scope.token = $stateParams.token;

            $scope.init = function(){
            	if($stateParams.token=="")
            		$window.location.href = 'https://localhost:8087/login';
            }


        }
})();