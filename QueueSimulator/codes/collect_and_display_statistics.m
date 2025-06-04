% collect_and_display_statistics.m
% Collect and display statistics

function collect_and_display_statistics(num_cars, service_times, bay_assignments, car_service_times, inter_arrival_times, waiting_times, system_times, arrival_times, start_times, end_times)
    % Collect service time statistics for each bay
    % Initialize structure for service time statistics
    service_time_stats = cell(3, 1);
    for bay = 1:3
        service_time_stats{bay} = struct('ServiceTimes', [], 'Cars', [], 'Probabilities', [], 'CDF', []);
    end

    % Collect service time statistics
    for i = 1:num_cars
        bay = bay_assignments(i);
        service_time_stats{bay}.ServiceTimes(end + 1) = car_service_times(i);
        service_time_stats{bay}.Cars(end + 1) = i;
    end

    % Calculate probabilities and CDF for each bay
    for bay = 1:3
        unique_service_times = unique(service_time_stats{bay}.ServiceTimes);
        prob = hist(service_time_stats{bay}.ServiceTimes, unique_service_times) / numel(service_time_stats{bay}.ServiceTimes);
        cdf = cumsum(prob);

        service_time_stats{bay}.Probabilities = prob;
        service_time_stats{bay}.CDF = cdf;
    end

    % Generate the overall simulation table separated by wash bays
    for bay = 1:3
        fprintf('Wash bay %d:\n', bay);
        fprintf('%-10s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n', ...
            'Car', 'Inter-Arr Time', 'Arrival Time', 'Service Time', 'Start Time', 'Waiting Time', 'Time in System', 'End Time');
        for i = 1:num_cars
            if bay_assignments(i) == bay
                fprintf('%-10d %-15.4f %-15.4f %-15.4f %-15.4f %-15.4f %-15.4f %-15.4f\n', ...
                    i, inter_arrival_times(i), arrival_times(i), car_service_times(i), start_times(i), waiting_times(i), system_times(i), end_times(i));
            end
        end
        fprintf('------------------------------------------------------------------------\n');
    end

    % Evaluate results of the simulation

    % 1. Average Waiting Time of a Car Owner
    average_waiting_time = mean(waiting_times);

    % 2. Average Inter-Arrival Time
    average_inter_arrival_time = mean(inter_arrival_times);

    % 3. Average Arrival Time
    average_arrival_time = mean(arrival_times);

    % 4. Average Time Spent in the System
    average_time_spent = mean(system_times);

    % 5. Probability that a Car Owner has to Wait in the Queue
    num_cars_waiting = sum(waiting_times > 0);
    probability_waiting = num_cars_waiting / num_cars;

    % 6. Average Service Time for Each Server
    average_service_time = mean(service_times);

    % Display overall metrics
    fprintf('\nOverall Metrics:\n');
    fprintf('%-20s %-20s %-20s %-20s %-20s %-20s\n', 'Number of Cars', 'Avg Waiting Time', 'Avg Inter-Arrival Time', 'Avg Arrival Time', 'Avg Time Spent', 'Probability Waiting');
    fprintf('%-20d %-20.4f %-20.4f %-20.4f %-20.4f %-20.4f\n', num_cars, average_waiting_time, average_inter_arrival_time, average_arrival_time, average_time_spent, probability_waiting);
    fprintf('------------------------------------------------------------------------\n');
    fprintf('Avg Service Time for Each Server:\n');
    fprintf('%-20s %-20s %-20s\n', 'Bay 1', 'Bay 2', 'Bay 3');
    fprintf('%-20.4f %-20.4f %-20.4f\n', average_service_time(1), average_service_time(2), average_service_time(3));
    fprintf('------------------------------------------------------------------------\n');

    % Display service time stats for each bay (Bay table)
    fprintf('Wash Bay Statistics:\n');
    for bay = 1:3
        stats = service_time_stats{bay};
        fprintf('------------------------------------------------------------------------\n');
        fprintf('Wash Bay %d:\n', bay);
        fprintf('%-15s %-15s %-15s %-20s %-15s\n', 'Service Time', 'Car', 'Probability', 'CDF', 'Range');
        for j = 1:numel(stats.ServiceTimes)
            if j == 1
                cdf_range_start = 0;
            else
                cdf_range_start = stats.CDF(j-1);
            end
            cdf_range_end = stats.CDF(j);

            % Format the range for cleaner output without %
            range_str = sprintf('%d - %d', round(cdf_range_start * 100), round(cdf_range_end * 100));

            fprintf('%-15.4f %-15d %-15.4f %-20.4f %-15s\n', ...
                stats.ServiceTimes(j), stats.Cars(j), stats.Probabilities(j), stats.CDF(j), range_str);
        end
    end
    fprintf('------------------------------------------------------------------------\n');

    % Display inter-arrival time statistics table
    inter_arrival_unique = unique(inter_arrival_times);
    inter_arrival_probabilities = hist(inter_arrival_times, inter_arrival_unique) / numel(inter_arrival_times); % Probability distribution
    inter_arrival_cdf = cumsum(inter_arrival_probabilities); % Cumulative distribution function

    fprintf('Inter-Arrival Time Statistics:\n');
    fprintf('%-15s %-15s %-15s %-20s\n', 'Inter-Arr Time', 'Probability', 'CDF', 'Range');
    for k = 1:numel(inter_arrival_unique)
        if k == 1
            cdf_range_start = 0;
        else
            cdf_range_start = inter_arrival_cdf(k-1);
        end
        cdf_range_end = inter_arrival_cdf(k);
        fprintf('%-15.4f %-15.4f %-15.4f %-20s\n', ...
            inter_arrival_unique(k), inter_arrival_probabilities(k), inter_arrival_cdf(k), sprintf('%d-%d', round(cdf_range_start * 100), round(cdf_range_end * 100)));
    end
    fprintf('------------------------------------------------------------------------\n');
end
