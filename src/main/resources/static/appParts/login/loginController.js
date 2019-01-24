(function() { "use strict";

    homeModule.controller('homeController', [ '$scope','$window','$localStorage','$location', '$stateParams',
        function($scope, $window, $localStorage, $location, $stateParams) {
            $scope.token = $stateParams.token;

            $scope.init = function(){
            	if($stateParams.token=="")
            		$window.location.href = 'https://localhost:8087/login';
            }


        }
    ]);
})();