% display_main_table.m
% Display the main car simulation table

function display_main_table(num_cars, inter_arrival_times, arrival_times, car_service_times, start_times, waiting_times, system_times, end_times, bay_assignments, car_service_types)
    fprintf('------------------------------------------------------------------------\n');
    fprintf('Main Car Simulation Table:\n');
    fprintf('%-10s %-15s %-15s %-20s %-15s %-15s %-15s %-15s %-15s\n', ...
        'Car', 'Inter-Arr Time', 'Arrival Time', 'Service Type', 'Service Time', 'Start Time', 'Waiting Time', 'Time in System', 'End Time');
    for i = 1:num_cars
        bay = bay_assignments(i); % Get assigned bay for the current car
        fprintf('%-10d %-15.4f %-15.4f %-20s %-15.4f %-15.4f %-15.4f %-15.4f %-15.4f\n', ...
            i, inter_arrival_times(i), arrival_times(i), car_service_types{i}, car_service_times(i), start_times(i), waiting_times(i), system_times(i), end_times(i));
    end
    fprintf('------------------------------------------------------------------------\n');
end
