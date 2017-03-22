(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('torneo', {
            parent: 'entity',
            url: '/torneo',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bisaiApp.torneo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/torneo/torneos.html',
                    controller: 'TorneoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('torneo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('torneo-detail', {
            parent: 'entity',
            url: '/torneo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bisaiApp.torneo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/torneo/torneo-detail.html',
                    controller: 'TorneoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('torneo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Torneo', function($stateParams, Torneo) {
                    return Torneo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'torneo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('torneo-detail.edit', {
            parent: 'torneo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/torneo/torneo-dialog.html',
                    controller: 'TorneoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Torneo', function(Torneo) {
                            return Torneo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('torneo.new', {
            parent: 'torneo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/torneo/torneo-dialog.html',
                    controller: 'TorneoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                numeroParticipantes: null,
                                fechaInicio: null,
                                cancelado: null,
                                descripcion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('torneo', null, { reload: 'torneo' });
                }, function() {
                    $state.go('torneo');
                });
            }]
        })
        .state('torneo.edit', {
            parent: 'torneo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/torneo/torneo-dialog.html',
                    controller: 'TorneoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Torneo', function(Torneo) {
                            return Torneo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('torneo', null, { reload: 'torneo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('torneo.delete', {
            parent: 'torneo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/torneo/torneo-delete-dialog.html',
                    controller: 'TorneoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Torneo', function(Torneo) {
                            return Torneo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('torneo', null, { reload: 'torneo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
