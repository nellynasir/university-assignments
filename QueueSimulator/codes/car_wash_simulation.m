% car_wash_simulation.m
% Simulate the car wash process

function [bay_assignments, arrival_times, start_times, end_times, waiting_times, system_times, car_service_times] = ...
    car_wash_simulation(num_cars, service_times, inter_arrival_times)

    % Initialize simulation variables
    arrival_times = cumsum(inter_arrival_times); % Arrival times based on inter-arrival times
    start_times = zeros(num_cars, 1); % Start times for each car
    end_times = zeros(num_cars, 1); % End times for each car
    waiting_times = zeros(num_cars, 1); % Waiting times for each car
    system_times = zeros(num_cars, 1); % Time spent in the system for each car
    bay_assignments = zeros(num_cars, 1); % Bay assignments for each car
    car_service_times = zeros(num_cars, 1); % Service times linked to each car

    % Initialize queue end times for each bay
    bay_end_times = zeros(3, 1);

    % Simulate the process
    for i = 1:num_cars
        % Determine arrival time
        arrival_time = arrival_times(i);

        % Determine which bay the car will use (1, 2, or 3)
        bay = mod(i-1, 3) + 1; % Cycle through bays 1, 2, 3 in order

        % Assign the car to the chosen bay
        bay_assignments(i) = bay;

        % Find the first available time slot in the wash bay
        start_time = max(bay_end_times(bay), arrival_time);

        % Calculate end time for the current car at the chosen bay
        end_time = start_time + service_times(i, bay);

        % Store start and end times
        start_times(i) = start_time;
        end_times(i) = end_time;

        % Calculate waiting time
        waiting_times(i) = start_time - arrival_time;

        % Calculate time spent in the system
        system_times(i) = end_time - arrival_time;

        % Update the end time for the chosen bay
        bay_end_times(bay) = end_time;

        % Store service time linked to the car
        car_service_times(i) = service_times(i, bay);
    end

    % Print messages after the simulation
    for i = 1:num_cars
        fprintf('Arrival of car %d at minute %.2f and queue at bay %d\n', i, arrival_times(i), bay_assignments(i));
        fprintf('Service for car %d started at minute %.2f.\n', i, start_times(i));
        fprintf('Departure of car %d at minute %.2f.\n', i, end_times(i));
    end
end

