(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('TorneoDetailController', TorneoDetailController);

    TorneoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Torneo', 'Juego', 'Jugador', 'Local', 'Equipo', 'Clasificacion', 'Partida'];

    function TorneoDetailController($scope, $rootScope, $stateParams, previousState, entity, Torneo, Juego, Jugador, Local, Equipo, Clasificacion, Partida) {
        var vm = this;

        vm.torneo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bisaiApp:torneoUpdate', function(event, result) {
            vm.torneo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
