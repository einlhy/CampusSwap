# CampusSwap
校园二手交易平台
// 2025 年终极正确姿势（三件套，缺一不可）
1. @TableName(autoResultMap = true)          // 必须！
2. @TableField(typeHandler = JacksonTypeHandler.class)  // 必须！
3. 字段初始化 private List<String> xxx = new ArrayList<>(); // 推荐！