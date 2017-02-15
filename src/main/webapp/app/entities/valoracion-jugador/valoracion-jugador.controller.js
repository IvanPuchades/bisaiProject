(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('ValoracionJugadorController', ValoracionJugadorController);

    ValoracionJugadorController.$inject = ['$scope', '$state', 'ValoracionJugador'];

    function ValoracionJugadorController ($scope, $state, ValoracionJugador) {
        var vm = this;

        vm.valoracionJugadors = [];

        loadAll();

        function loadAll() {
            ValoracionJugador.query(function(result) {
                vm.valoracionJugadors = result;
                vm.searchQuery = null;
            });
        }
    }
})();
