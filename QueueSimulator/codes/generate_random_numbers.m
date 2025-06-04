% generate_random_numbers.m
% Generate random numbers for service times, inter-arrival times, and service types

function [service_times, inter_arrival_times, car_service_types, service_cdf, service_ranges] = generate_random_numbers(num_cars, rng_choice, service_type_probabilities)
    % Define service types and their probabilities
    service_types = {'Automatic', 'Full-Service', 'Detailing'};
    service_cdf = cumsum(service_type_probabilities);
    service_ranges = cell(1, length(service_types));
    for i = 1:length(service_types)
        if i == 1
            service_ranges{i} = sprintf('[0, %.2f)', service_cdf(i));
        else
            service_ranges{i} = sprintf('[%.2f, %.2f)', service_cdf(i-1), service_cdf(i));
        end
    end
    
    % Pre-allocate arrays
    service_times = zeros(num_cars, 3); % Service times for each car at three wash bays
    inter_arrival_times = zeros(num_cars, 1); % Inter-arrival times for each car
    car_service_types = cell(num_cars, 1); % Service type for each car

    switch rng_choice
        case 1
            % LCG parameters
            seed = input('Enter seed for LCG: ');
            m = 2^31 - 1; % Modulus
            a = 7^5; % Multiplier
            c = 0; % Increment

            % Initialize LCG
            rand_state = mod(a * seed + c, m);

            % Generate random numbers using LCG
            for i = 1:num_cars
                rand_state = mod(a * rand_state + c, m);
                service_times(i, :) = rand_state / m * 10; % Scale to [0, 10]

                rand_state = mod(a * rand_state + c, m);
                inter_arrival_times(i) = rand_state / m * 5; % Scale to [0, 5]

                rand_state = mod(a * rand_state + c, m);
                % Generate random number for service type
                service_type_rand = rand_state / m;

                % Assign service type based on the generated random number
                for j = 1:length(service_cdf)
                    if service_type_rand < service_cdf(j)
                        car_service_types{i} = service_types{j};
                        break;
                    end
                end
            end

        case 2
            % Generate random numbers using built-in rand function
            for i = 1:num_cars
                service_times(i, :) = rand(1, 3) * 10; % Scale to [0, 10]
                inter_arrival_times(i) = rand * 5; % Scale to [0, 5]

                % Generate random number for service type
                service_type_rand = rand;

                % Assign service type based on the generated random number
                for j = 1:length(service_cdf)
                    if service_type_rand < service_cdf(j)
                        car_service_types{i} = service_types{j};
                        break;
                    end
                end
            end

        otherwise
            error('Invalid choice. Please enter 1 for LCG or 2 for rand.');
    end
end





