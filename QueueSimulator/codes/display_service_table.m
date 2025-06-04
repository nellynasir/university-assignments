% display_service_table.m
% Display the car wash service table

function display_service_table(service_types, service_type_probabilities, service_cdf, service_ranges, car_service_types)
    fprintf('------------------------------------------------------------------------\n');
    fprintf('Car Wash Service Table:\n');
    fprintf('%-20s %-15s %-15s %-15s\n', 'Service Type', 'Probability', 'CDF', 'Range');
    for i = 1:length(service_types)
        fprintf('%-20s %-15.2f %-15.2f %-15s\n', ...
            service_types{i}, service_type_probabilities(i), service_cdf(i), service_ranges{i});
    end
    fprintf('------------------------------------------------------------------------\n');
    fprintf('Assigned Service Types to Cars:\n');
    fprintf('%-10s %-20s\n', 'Car', 'Service Type');
    for i = 1:length(car_service_types)
        fprintf('%-10d %-20s\n', i, car_service_types{i});
    end
    fprintf('------------------------------------------------------------------------\n');
end


