(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('EquipoController', EquipoController);

    EquipoController.$inject = ['$scope', '$state', 'Equipo'];

    function EquipoController ($scope, $state, Equipo) {
        var vm = this;

        vm.equipos = [];

        loadAll();

        function loadAll() {
            Equipo.query(function(result) {
                vm.equipos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
