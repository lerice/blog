class AddCommenteridToComment < ActiveRecord::Migration
  def change
    add_column :comments, :commenterid, :int
  end
end
