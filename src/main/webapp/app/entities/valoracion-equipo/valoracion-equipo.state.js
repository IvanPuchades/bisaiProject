(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('valoracion-equipo', {
            parent: 'entity',
            url: '/valoracion-equipo',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bisaiApp.valoracionEquipo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/valoracion-equipo/valoracion-equipos.html',
                    controller: 'ValoracionEquipoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('valoracionEquipo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('valoracion-equipo-detail', {
            parent: 'entity',
            url: '/valoracion-equipo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bisaiApp.valoracionEquipo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/valoracion-equipo/valoracion-equipo-detail.html',
                    controller: 'ValoracionEquipoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('valoracionEquipo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ValoracionEquipo', function($stateParams, ValoracionEquipo) {
                    return ValoracionEquipo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'valoracion-equipo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('valoracion-equipo-detail.edit', {
            parent: 'valoracion-equipo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valoracion-equipo/valoracion-equipo-dialog.html',
                    controller: 'ValoracionEquipoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ValoracionEquipo', function(ValoracionEquipo) {
                            return ValoracionEquipo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('valoracion-equipo.new', {
            parent: 'valoracion-equipo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valoracion-equipo/valoracion-equipo-dialog.html',
                    controller: 'ValoracionEquipoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                hora: null,
                                meGusta: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('valoracion-equipo', null, { reload: 'valoracion-equipo' });
                }, function() {
                    $state.go('valoracion-equipo');
                });
            }]
        })
        .state('valoracion-equipo.edit', {
            parent: 'valoracion-equipo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valoracion-equipo/valoracion-equipo-dialog.html',
                    controller: 'ValoracionEquipoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ValoracionEquipo', function(ValoracionEquipo) {
                            return ValoracionEquipo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('valoracion-equipo', null, { reload: 'valoracion-equipo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('valoracion-equipo.delete', {
            parent: 'valoracion-equipo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valoracion-equipo/valoracion-equipo-delete-dialog.html',
                    controller: 'ValoracionEquipoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ValoracionEquipo', function(ValoracionEquipo) {
                            return ValoracionEquipo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('valoracion-equipo', null, { reload: 'valoracion-equipo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
