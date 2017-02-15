(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('PorraDetailController', PorraDetailController);

    PorraDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Porra', 'Jugador', 'Partida'];

    function PorraDetailController($scope, $rootScope, $stateParams, previousState, entity, Porra, Jugador, Partida) {
        var vm = this;

        vm.porra = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bisaiApp:porraUpdate', function(event, result) {
            vm.porra = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
