(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('JugadorController', JugadorController);

    JugadorController.$inject = ['$scope', '$state', 'Jugador'];

    function JugadorController ($scope, $state, Jugador) {
        var vm = this;

        vm.jugadors = [];

        loadAll();

        function loadAll() {
            Jugador.query(function(result) {
                vm.jugadors = result;
                vm.searchQuery = null;
            });
        }
    }
})();
