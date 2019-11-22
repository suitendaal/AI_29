function [Agents, Data, Total, Util ] = csvToData2(csvName)
%UNTITLED4 Summary of this function goes here
%   Detailed explanation goes here
filename=csvName;
m = readtable(filename,...
    'Delimiter',';','ReadVariableNames',true);
Util1=m.Utility1;
Util2=m.Utility2;
Util=[Util1, Util2];
for i=1:length(m.Agent1)  
    str=m.Agent1{i};
    Agent1(i) = convertCharsToStrings(extractBefore(str,"@"));
    str=m.Agent2{i};
    Agent2(i) = convertCharsToStrings(extractBefore(str,"@"));
end

Agents  = [Agent1; Agent2];
AgentsI = [Agent1; Agent2]';

Data    = [m.min_util_, m.max_util_, m.Dist_ToPareto, m.Dist_ToNash];
Total   = [AgentsI Data];
TotalS  = sort(Total,1); %sort on the first dimension
end

