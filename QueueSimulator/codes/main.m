% main.m
% FreeMat Car Wash Queue Simulator with LCG option

% Prompt user for input
num_cars = input('Enter the number of cars to simulate: ');
rng_choice = input('Choose random number generator (1 for LCG, 2 for rand): ');

% Define service types and their probabilities
service_types = {'Automatic', 'Full-Service', 'Detailing'};
service_type_probabilities = [0.3, 0.4, 0.3];

% Generate random numbers for service times, inter-arrival times, and service types
[service_times, inter_arrival_times, car_service_types, service_cdf, service_ranges] = generate_random_numbers(num_cars, rng_choice, service_type_probabilities);

% Display the car wash service table
display_service_table(service_types, service_type_probabilities, service_cdf, service_ranges, car_service_types);

% Run the car wash simulation
[bay_assignments, arrival_times, start_times, end_times, waiting_times, system_times, car_service_times] = ...
    car_wash_simulation(num_cars, service_times, inter_arrival_times);

% Display main car simulation table
display_main_table(num_cars, inter_arrival_times, arrival_times, car_service_times, start_times, waiting_times, system_times, end_times, bay_assignments, car_service_types);

% Collect and display statistics
collect_and_display_statistics(num_cars, service_times, bay_assignments, car_service_times, inter_arrival_times, waiting_times, system_times, arrival_times, start_times, end_times);

