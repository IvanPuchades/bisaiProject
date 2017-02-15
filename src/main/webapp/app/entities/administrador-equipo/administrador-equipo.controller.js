(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('AdministradorEquipoController', AdministradorEquipoController);

    AdministradorEquipoController.$inject = ['$scope', '$state', 'AdministradorEquipo'];

    function AdministradorEquipoController ($scope, $state, AdministradorEquipo) {
        var vm = this;

        vm.administradorEquipos = [];

        loadAll();

        function loadAll() {
            AdministradorEquipo.query(function(result) {
                vm.administradorEquipos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
