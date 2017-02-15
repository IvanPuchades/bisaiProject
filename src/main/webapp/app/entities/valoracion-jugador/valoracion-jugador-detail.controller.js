(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('ValoracionJugadorDetailController', ValoracionJugadorDetailController);

    ValoracionJugadorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ValoracionJugador', 'Jugador'];

    function ValoracionJugadorDetailController($scope, $rootScope, $stateParams, previousState, entity, ValoracionJugador, Jugador) {
        var vm = this;

        vm.valoracionJugador = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bisaiApp:valoracionJugadorUpdate', function(event, result) {
            vm.valoracionJugador = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
