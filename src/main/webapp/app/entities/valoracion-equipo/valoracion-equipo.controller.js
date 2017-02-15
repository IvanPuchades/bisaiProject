(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('ValoracionEquipoController', ValoracionEquipoController);

    ValoracionEquipoController.$inject = ['$scope', '$state', 'ValoracionEquipo'];

    function ValoracionEquipoController ($scope, $state, ValoracionEquipo) {
        var vm = this;

        vm.valoracionEquipos = [];

        loadAll();

        function loadAll() {
            ValoracionEquipo.query(function(result) {
                vm.valoracionEquipos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
