function [Agents, Data, TotalS, Util1, Util2 ] = csvToData(csvName)
%UNTITLED4 Summary of this function goes here
%   Detailed explanation goes here
filename=csvName;
m = readtable(filename,...
    'Delimiter',';','ReadVariableNames',true);
% m=table2array(m)
Util1=m.Utility1;
Util2=m.Utility2;
for i=1:length(m.Agent1)
    
    str=m.Agent1{i};
    Agent1(i) = convertCharsToStrings(extractBefore(str,"@"));
    
    if exist m.Agent2 %length(m.Agent1)==3
         str=m.Agent2{i};
        Agent2(i) = convertCharsToStrings(extractBefore(str,"@"));
    end
   
    if exist m.Agent3 %length(m.Agent1)==3
        str=m.Agent3{i};
        Agent3(i) = convertCharsToStrings(extractBefore(str,"@"));
    end
end
if exist m.Agent3 %length(m.Agent1)==3
    Agents  = [Agent1; Agent2; Agent3];
    AgentsI = [Agent1; Agent2; Agent3]';
elseif exist m.Agent2 %length(m.Agent1)==2
    Agents  = [Agent1; Agent2];
    AgentsI = [Agent1; Agent2]';
else
    Agents  = [Agent1];
    AgentsI = [Agent1]';
end

% Agents  = [Agent1; Agent2];
% AgentsI = [Agent1; Agent2]';
Data    = [m.min_util_, m.max_util_, m.Dist_ToPareto, m.Dist_ToNash];
% Data    = table2array(Data);
% Data = double(Data);
% Data = cell2mat(Data);
Total   = [AgentsI Data];
TotalS  = sort(Total,1); %sort on the first dimension
end

